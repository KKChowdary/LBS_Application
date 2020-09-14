from flask import Flask, render_template, Response,request
from  Generator import Generator
import cv2
import os
import imutils
import logging
app = Flask(__name__)

from config import MODEL_PATH
from config import NMS_THRESH
from config import MIN_CONF
from config import USE_GPU

labelsPath = os.path.sep.join([MODEL_PATH, "coco.names"])
LABELS = open(labelsPath).read().strip().split("\n")
weightsPath = os.path.sep.join([MODEL_PATH, "yolov3.weights"])
configPath = os.path.sep.join([MODEL_PATH, "yolov3.cfg"])
net = cv2.dnn.readNetFromDarknet(configPath, weightsPath)
if USE_GPU:
	net.setPreferableBackend(cv2.dnn.DNN_BACKEND_CUDA)
	net.setPreferableTarget(cv2.dnn.DNN_TARGET_CUDA)
ln = net.getLayerNames()
ln = [ln[i[0] - 1] for i in net.getUnconnectedOutLayers()]
		

gen=None



@app.route("/pvideo_feed")
def pvideo_feed():
	global gen

	return Response(gen.getVideo(),mimetype = "multipart/x-mixed-replace; boundary=frame")


@app.route('/<protocol>/<ip>/<port>/<path>')
def index(protocol,ip,port,path):
	gen=Generator(protocol+'://'+ip+':'+port+'/'+path,ln,net)
	# waiting to get initial frames
	return render_template('index.html')

@app.route('/')
def indexs():
	global gen
	protocol=request.args.get('protocol',default='rtsp', type=str)
	ip=request.args.get('ip',default='127.0.0.1', type=str)
	port=request.args.get('port',default='554', type=str)
	path=request.args.get('path',default='', type=str)
	url=protocol+'://'+ip+':'+port+'/'+path
	print(url)
	gen=Generator(url,ln,net)
	return render_template('index.html')


if __name__ == '__main__':
	app.run()
	


