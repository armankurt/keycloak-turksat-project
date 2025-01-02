<#import "template.ftl" as layout>

<@layout.registrationLayout displayInfo=true displayMessage=!messagesPerField.existsError('username'); section>

    <#-- Header Section -->
    <#if section == "header">
        <div id="kc-header">
            <img src="${url.resourcesPath}/img/logo.png" alt="Logo" class="logo" />
            <h1 class="page-title">Şifre Sıfırlama</h1>
            <p class="page-subtitle">Hesabınızı güvenle sıfırlamak için e-posta adresinizi girin.</p>
        </div>

    <#-- Form Section -->
    <#elseif section == "form">
        <div id="kc-reset-password">
            <form id="kc-reset-password-form" action="${url.loginAction}" method="post">
                <div class="input-group">
                    <label for="username" class="input-label">Kullanıcı Adı veya E-posta</label>
                    <input type="text" id="username" name="username" class="input-field" 
                           autofocus placeholder="örnek@projemkapisi.com" 
                           value="${(auth.attemptedUsername!'')}" />
                    <#if messagesPerField.existsError('username')>
                        <span class="error-message">
                            ${kcSanitize(messagesPerField.get('username'))?no_esc}
                        </span>
                    </#if>
                </div>
                <div class="button-group">
                    <a href="${url.loginUrl}" class="btn-secondary">Giriş Sayfasına Dön</a>
                    <button type="submit" class="btn-primary">Gönder</button>
                </div>
            </form>
        </div>

    <#-- Info Section -->
    <#elseif section == "info">
        <div id="kc-info">
            <p class="info-text">
                E-posta adresinizi girdikten sonra şifre sıfırlama talimatlarını içeren bir e-posta alacaksınız.
            </p>
        </div>
    </#if>

    <style>
        /* Genel Stil */
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f4f7f9;
            color: #333;
            margin: 0;
        }

        #kc-header {
            text-align: center;
            margin-bottom: 20px;
        }

        .logo {
            width: 80px;
            margin-bottom: 10px;
        }

        .page-title {
            font-size: 28px;
            font-weight: bold;
            color: #333;
        }

        .page-subtitle {
            font-size: 16px;
            color: #666;
            margin-bottom: 30px;
        }

        #kc-reset-password {
            background: #ffffff;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            max-width: 400px;
            margin: 0 auto;
            padding: 30px;
        }

        .input-group {
            margin-bottom: 20px;
        }

        .input-label {
            display: block;
            margin-bottom: 8px;
            font-weight: 600;
            color: #444;
        }

        .input-field {
            width: 100%;
            padding: 12px;
            border: 1px solid #ccc;
            border-radius: 6px;
            font-size: 14px;
            box-sizing: border-box;
        }

        .input-field:focus {
            border-color: #007bff;
            outline: none;
            box-shadow: 0 0 5px rgba(0, 123, 255, 0.5);
        }

        .error-message {
            color: #e74c3c;
            font-size: 12px;
            margin-top: 5px;
        }

        .button-group {
            display: flex;
            justify-content: space-between;
            gap: 10px;
        }

        .btn-primary, .btn-secondary {
            display: inline-block;
            padding: 12px;
            text-align: center;
            border-radius: 6px;
            font-size: 14px;
            font-weight: 600;
            cursor: pointer;
            text-decoration: none;
        }

        .btn-primary {
            background-color: #007bff;
            color: #fff;
            border: none;
        }

        .btn-primary:hover {
            background-color: #0056b3;
        }

        .btn-secondary {
            background-color: #e0e0e0;
            color: #333;
            border: none;
        }

        .btn-secondary:hover {
            background-color: #ccc;
        }

        .info-text {
            text-align: center;
            color: #555;
            font-size: 14px;
            margin-top: 20px;
        }
    </style>

</@layout.registrationLayout>
