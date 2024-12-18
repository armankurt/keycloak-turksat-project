from flask import Flask, jsonify, request
from flask_cors import CORS

app = Flask(__name__)
CORS(app)  # Tüm route'lar için CORS'u etkinleştir


app = Flask(__name__)

# Kullanıcı onayı durumu
approval_status = {"approved": False}

# Doğru şifre
CORRECT_PASSWORD = "1234"

@app.route("/mobil-sign/approval", methods=["GET"])
def get_approval_status():
    if approval_status["approved"]:
        return jsonify({"status": "approved"}), 200
    return jsonify({"status": "pending"}), 202

@app.route("/mobil-sign/approve", methods=["POST"])
def approve():
    try:
        data = request.get_json()
        print("Gelen veri:", data)  # Gelen JSON'u kontrol etmek için log ekle
        password = data.get("password", "")

        if password == CORRECT_PASSWORD:
            approval_status["approved"] = True
            return jsonify({"message": "User approved"}), 200
        else:
            return jsonify({"message": "Invalid password"}), 401
    except Exception as e:
        print("Hata:", str(e))
        return jsonify({"message": "Invalid request format"}), 400

if __name__ == "__main__":
    app.run(port=8081)