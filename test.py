import base64
import os
# import RPi.GPIO as GPIO
import requests
import pygame

BUTTON = 23
# SERVER_IP = "35.244.107.42"

def setup():
    GPIO.setmode(GPIO.BCM)
    GPIO.setwarnings(False)
    GPIO.setup(BUTTON, GPIO.IN, pull_up_down=GPIO.PUD_DOWN)

def callback():
    # os.system("wget http://127.0.0.1:8080/?action=snapshot -O output.png")
    url = "http://127.0.0.1:8084/Recognize"
    with open("1.jpg","rb") as f:
        img = base64.b64encode(f.read()).decode()
    data = {"img" : img}
    response = requests.post(url,data=data)
    if response.text != "null":
        # print(response.text)
        with open("output.mp3", "wb") as f:
            f.write(base64.b64decode(response.text))
        
        pygame.mixer.init()
        pygame.mixer.music.load("output.mp3")
        pygame.mixer.music.play()
        while pygame.mixer.music.get_busy() == True:
            continue

if __name__ == "__main__":
    # setup()
    # GPIO.add_event_detect(BUTTON, GPIO.RISING, callback=callback, bouncetime=200)
    callback()