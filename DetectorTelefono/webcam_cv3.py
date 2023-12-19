import cv2
import sys
import logging as log
import datetime as dt
from time import sleep

cascPath = "entreno_telefono.xml"
faceCascade = cv2.CascadeClassifier(cascPath)
log.basicConfig(filename='webcam.log',level=log.INFO)

video_capture = cv2.VideoCapture(0)
anterior = 0

while True:
    if not video_capture.isOpened():
        print('Unable to load camera.')
        sleep(5)
        pass

    # Capture frame-by-frame
    ret, frame = video_capture.read()

    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)

    phone = faceCascade.detectMultiScale(
        gray,
        scaleFactor=10,
        minNeighbors=5,
        minSize=(100, 200)
    )

    # Draw a rectangle around the phone
    for (x, y, w, h) in phone:
        cv2.rectangle(frame, (x, y), (x+w, y+h), (0, 255, 0), 2)

    if anterior != len(phone):
        anterior = len(phone)
        log.info("phone: "+str(len(phone))+" at "+str(dt.datetime.now()))


    # Display the resulting frame
    cv2.imshow('Video', frame)


    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

    # Display the resulting frame
    cv2.imshow('Video', frame)

# When everything is done, release the capture
video_capture.release()
cv2.destroyAllWindows()
