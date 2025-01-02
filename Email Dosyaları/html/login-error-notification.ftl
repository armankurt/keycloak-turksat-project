<!DOCTYPE html>
<html>
<head>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            padding: 20px;
        }
        .email-container {
            background-color: #ffffff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        .email-header {
            font-size: 24px;
            font-weight: bold;
            color: #333333;
        }
        .email-body {
            font-size: 16px;
            color: #555555;
        }
        .email-footer {
            margin-top: 20px;
            font-size: 14px;
            color: #999999;
        }
    </style>
</head>
<body>
    <div class="email-container">
        <div class="email-header">Giriş Başarısız Oldu</div>
        <div class="email-body">
            <p>Merhaba ${user.firstName},</p>
            <p>Sistemimize yaptığınız giriş başarısız oldu. Lütfen şifrenizi kontrol edin ve tekrar deneyin.</p>
        </div>
        <div class="email-footer">
            Saygılarımızla,<br>
            ${realmName} Ekibi
        </div>
    </div>
</body>
</html>
