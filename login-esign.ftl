<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>E-İmza ile Giriş</title>
    <link rel="stylesheet" href="${url.resourcesPath}/login/style.css">
</head>
<body>
    <div id="kc-form">
        <div id="kc-form-wrapper">
            <h1>E-İmza ile Giriş</h1>
            <p>E-İmza doğrulama işlemini başlatmak için aşağıdaki butona tıklayın:</p>

            <!-- E-İmza Giriş Butonu -->
            <button id="esign-login" class="btn-extra">
                E-İmza ile Giriş Yap
            </button>
        </div>
    </div>

    <script type="text/javascript">
        document.getElementById('esign-login').addEventListener('click', function () {
            // Keycloak e-imza doğrulama URL'sine yönlendirme
            const authUrl = "${url.loginAction}?execution=e-imza";
            window.location.href = authUrl;
        });
    </script>

    <style>
        /* Basit stil ayarları */
        #kc-form {
            text-align: center;
            margin: 50px auto;
            width: 300px;
        }

        .btn-extra {
            background-color: #007bff;
            color: white;
            border: none;
            padding: 10px 20px;
            font-size: 16px;
            border-radius: 5px;
            cursor: pointer;
        }

        .btn-extra:hover {
            background-color: #0056b3;
        }
    </style>
</body>
</html>
