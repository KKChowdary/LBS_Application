import cv2
import threading
import imutils
import numpy as np
import base64
from config import MODEL_PATH
from config import NMS_THRESH
from config import MIN_CONF
from config import USE_GPU
import os

class Generator:
	def __init__(self, url,ln,net):
		self.url=url
		self.outputFrame=None
		self.iframe=None;
		self.count=0
		labelsPath = os.path.sep.join([MODEL_PATH, "coco.names"])
		LABELS = open(labelsPath).read().strip().split("\n")
		weightsPath = os.path.sep.join([MODEL_PATH, "yolov3.weights"])
		configPath = os.path.sep.join([MODEL_PATH, "yolov3.cfg"])
		self.net = cv2.dnn.readNetFromDarknet(configPath, weightsPath)
		if USE_GPU:
			self.net.setPreferableBackend(cv2.dnn.DNN_BACKEND_CUDA)
			self.net.setPreferableTarget(cv2.dnn.DNN_TARGET_CUDA)
		self.ln = self.net.getLayerNames()
		self.ln = [self.ln[i[0] - 1] for i in self.net.getUnconnectedOutLayers()]
		self.vs = cv2.VideoCapture(self.url)
		self.t1 = threading.Thread(target=self.preExecute)
		self.t1.start()
		self.t2 = threading.Thread(target=self.execute)
		self.t2.start()

	def preExecute(self):
		while True:
			(grabbed, frame) = self.vs.read()
			if grabbed :
				frame = imutils.resize(frame, width=400)
				self.iframe=frame.copy()


	def execute(self):
		while True:
			if self.iframe is None :
				continue
			frame = self.iframe.copy()
			frame = imutils.resize(frame, width=400)
			results=self.detect_people(frame,self.net, self.ln)
			color = (0, 255, 0)
			self.count=len(results)
			for (i, (prob, bbox, centroid)) in enumerate(results.copy()):
				(startX, startY, endX, endY) = bbox
				(cX, cY) = centroid
				cv2.rectangle(frame, (startX,startY),(endX,endY), color, 2)
			text = "Person Count: {}".format(self.count)
			cv2.putText(frame, text, (10, frame.shape[0] - 25),cv2.FONT_HERSHEY_SIMPLEX, 0.85, (0, 255, 255), 2)
			self.outputFrame=frame.copy()
	

	def getJson(self):
		if self.outputFrame is None:
			return  (dict(status = 'failed'))
		(flag, encodedImage) = cv2.imencode(".jpg", self.outputFrame)
		ee=base64.b64encode(bytearray(encodedImage)).decode('ascii')
		return  (dict(status = 'success', message = self.count ,bytes=ee))
	
	def detect_people(self,frame, net, ln, personIdx=0):
		
		(H, W) = frame.shape[:2]
		results = []
		blob = cv2.dnn.blobFromImage(frame, 1 / 255.0, (416, 416),swapRB=True, crop=False)
		net.setInput(blob)
		layerOutputs = net.forward(ln)
		boxes = []
		centroids = []
		confidences = []
		for output in layerOutputs:
			for detection in output:
				scores = detection[5:]
				classID = np.argmax(scores)
				confidence = scores[classID]
				if classID == personIdx and confidence > MIN_CONF:
					box = detection[0:4] * np.array([W, H, W, H])
					(centerX, centerY, width, height) = box.astype("int")
					x = int(centerX - (width / 2))
					y = int(centerY - (height / 2))
					boxes.append([x, y, int(width), int(height)])
					centroids.append((centerX, centerY-(int)(height / 2)))
					confidences.append(float(confidence))

		idxs = cv2.dnn.NMSBoxes(boxes, confidences, MIN_CONF, NMS_THRESH)

		if len(idxs) > 0:
			for i in idxs.flatten():
				(x, y) = (boxes[i][0], boxes[i][1])
				(w, h) = (boxes[i][2], boxes[i][3])
				r = (confidences[i], (x, y, x + w, y + h), centroids[i])
				results.append(r)
		return results

			
	def getCount(self):
		return self.count


	def getVideo(self):
		while True:
			if self.outputFrame is None:
				continue
			(flag, encodedImage) = cv2.imencode(".jpg", self.outputFrame)
			yield(b'--frame\r\n' b'Content-Type: image/jpeg\r\n\r\n' + bytearray(encodedImage) + b'\r\n')
	