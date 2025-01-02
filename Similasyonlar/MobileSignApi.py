from flask import Flask, jsonify, request
from flask_cors import CORS

app = Flask(__name__)
CORS(app)  # Enable CORS for all routes and origins

# Approval status
approval_status = {"approved": False}

# Correct password
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
        password = data.get("password", "")

        if password == CORRECT_PASSWORD:
            approval_status["approved"] = True
            return jsonify({"message": "User approved"}), 200
        else:
            return jsonify({"message": "Invalid password"}), 401
    except Exception as e:
        return jsonify({"message": f"Invalid request format: {str(e)}"}), 400

if __name__ == "__main__":
    app.run(port=8081)
