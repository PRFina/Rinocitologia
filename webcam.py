from pyzbar.pyzbar import decode  # import decode module from zbar
from py4j.java_gateway import JavaGateway, GatewayParameters
import cv2  # and openCv

gateway = JavaGateway(gateway_parameters=GatewayParameters(port=25335))
patient = gateway.entry_point


def get_barcode_info(img):
    gray_img = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)  # turn it grey trough openCv
    barcodes = decode(gray_img)  # read barcodes in the image -> contains a list with all the barcodes
    # print(barcodes)  # print infos on the screen
    if len(barcodes) == 1:
        code = barcodes[0].data
        # code = barcodes[0].data
        print(code.decode("utf-8"))
        return "Codice Fiscale: {}".format(product["product_name"])
    else:
        return "Codice Fiscale non individuato! Premere il tasto q per uscire"

cap = cv2.VideoCapture(0)
while(True):
    ret, frame = cap.read()
    info = get_barcode_info(frame)
    cv2.putText(frame, info, (30, 30), cv2.FONT_HERSHEY_SIMPLEX, 1, (0, 255, 0), 2)
    cv2.imshow('Barcode', frame)
    code = cv2.waitKey(30)
    if code == ord('q'):
        break
