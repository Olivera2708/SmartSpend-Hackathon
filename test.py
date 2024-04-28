from http.server import BaseHTTPRequestHandler, HTTPServer
import time
import main
import json

hostName = "192.168.132.83"
serverPort = 8080

class MyServer(BaseHTTPRequestHandler):
    def do_GET(self):
        if self.path == '/spending_tip':
            self.send_response(200)
            self.send_header("Content-type", "application/json")
            self.end_headers()
            dict = {"value": main.get_spending_tip()}
            json_data = json.dumps(dict)
            self.wfile.write(json_data.encode("utf-8"))
        elif self.path == '/category_tip':
            self.send_response(200)
            self.send_header("Content-type", "application/json")
            self.end_headers()
            dict = {"value": main.get_category_tip()}
            json_data = json.dumps(dict)
            self.wfile.write(json_data.encode("utf-8"))
        elif self.path == "/get_all":
            self.send_response(200)
            self.send_header("Content-type", "application/json")
            self.end_headers()
            dict = {"value": main.get_all()}
            json_data = json.dumps(dict)
            self.wfile.write(json_data.encode("utf-8"))
        else:
            self.send_response(404)
            self.end_headers()
            self.wfile.write(b"404 Not Found")

    def do_POST(self):
        if self.path == "/category":
            content_length = int(self.headers['Content-Length'])
            post_data = self.rfile.read(content_length)
            data_string = post_data.decode("UTF-8")

            category_result = main.get_category(data_string)

            self.send_response(200)
            self.send_header('Content-type', 'application/json')
            self.end_headers()
            dict = {"value": category_result}
            json_data = json.dumps(dict)
            self.wfile.write(json_data.encode("utf-8"))
        elif self.path == "/add":
            content_length = int(self.headers['Content-Length'])
            post_data = self.rfile.read(content_length)
            data_string = post_data.decode("UTF-8")

            category_result = main.add(data_string)

            self.send_response(200)
            self.send_header('Content-type', 'application/json')
            self.end_headers()
            self.wfile.write(bytes(category_result,"utf-8"))
        elif self.path == "/chat":
            content_length = int(self.headers['Content-Length'])
            post_data = self.rfile.read(content_length)
            data_string = post_data.decode("UTF-8")

            category_result = main.chat(data_string)

            self.send_response(200)
            self.send_header('Content-type', 'application/json')
            self.end_headers()
            dict = {"value": category_result}
            json_data = json.dumps(dict)
            self.wfile.write(json_data.encode("utf-8"))
        else:
            self.send_response(404)
            self.end_headers()
            self.wfile.write(b"404 Not Found")

if __name__ == "__main__":        
    webServer = HTTPServer((hostName, serverPort), MyServer)
    print("Server started http://%s:%s" % (hostName, serverPort))

    try:
        webServer.serve_forever()
    except KeyboardInterrupt:
        pass

    webServer.server_close()
    print("Server stopped.")