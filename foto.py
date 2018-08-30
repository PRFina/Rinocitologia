from pyzbar.pyzbar import decode  # import decode module from zbar
from py4j.java_gateway import JavaGateway, GatewayParameters
import cv2  # and openCv

gateway = JavaGateway(gateway_parameters=GatewayParameters(port=25335))
patient = gateway.entry_point
# filename = "img.jpg"
filename = patient.getPathCFPhoto()
img = cv2.imread(filename)  # read and store the image in img
gray_img = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)  # turn it grey trough openCv
barcodes = decode(gray_img)
if len(barcodes) == 1:
    code = barcodes[0].data
    print(code.decode("utf-8"))
else:
    print("Codice Fiscale non trovato")

