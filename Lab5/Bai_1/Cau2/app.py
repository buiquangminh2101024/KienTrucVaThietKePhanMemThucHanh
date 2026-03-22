from flask import Flask

app = Flask(__name__)

@app.route('/')
def hello():
    return "Hello, Docker Flask!"

if __name__ == '__main__':
    # Chạy trên 0.0.0.0 để Docker có thể truy cập từ bên ngoài
    app.run(host='0.0.0.0', port=5000)