DNAS Firehouse API 

Firehose API provides multiple events such as device entry, exit, current location, associated profile and more. Cisco DNA Spaces partner would be able to integrate the Firehose API to consume these events to realise many use cases, one such use case is to view the current visitors of a location for a customer.

This sample application uses Cisco DNA Spaces Firehose API events such as entry, exit, current location and associated profile, builds data pipeline using  http requests to host in public cloud and private cloud.

Sample Application consists of 2 components namely

API Server
Client
Clone the Repository and follow below instructions to run the application.

Extract the  the nView\Cisco DNAS
Import in Java Ide .
update the Dnas production token from your DNAS account.


Webex 

This is a monorepo containing all officially maintained Cisco Webex JS SDK modules in the same repo. webex is a collection of node modules targeting webex Rest APIs.


Clone the Repository and follow below instructions to run the application.

Extract the  the Nview\webex 
Import in Java Ide .
update the barrer token from your webex account.

Meraki camera

This document provides a step by step guide to integrating the RTSP output from a Meraki MV camera, with a pretrained OpenCV 3.3 dnn module, running SSD Detection on the COCO dataset.

The script shall:

launch a local video stream, with a bounding box for detected objects and associated confidence score
print to cli the object and confidence score of detected objects
publish to an MQTT broker the object and confidence of objects detected, using the topic "python/run.py"
This was developed and tested using windows 10 . The detection time for objects entering the frame to being reported on the video and MQTT stream is of the order 1.5-2.0 seconds.

Dependencies
Python
This script was develeoped using python version 3.7.3

Meraki Camera Requirements
Firmware version 4.2 or newer 2nd generation MV camera For further details: https://documentation.meraki.com/MV/Advanced_Configuration/External_RTSP
