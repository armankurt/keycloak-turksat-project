<#-- Import template and stylesheet -->
<#import "template.ftl" as layout>
<link rel="stylesheet" href="${url.resourcesPath}/login/style.css">

<@layout.registrationLayout displayMessage=false displayInfo=false; section>

    <#-- Header Section -->
    <#if section == "header">
        <div id="kc-header">
            <img src="${url.resourcesPath}/img/wait.png" alt="Keycloak Logo" class="logo">
            <h1>Onay Bekleniyor</h1>
            <p>Kimlik doğrulama süreciniz için API üzerinden onay alınması bekleniyor.</p>
        </div>
    <#elseif section == "form">

        <div id="kc-headerr">
            <img src="${url.resourcesPath}/img/img.png" alt="Keycloak Logo" class="logo">
            <h1>Mobil İmza Onayı Bekleniyor</h1>
            <p>Lütfen işleminizi tamamlamak için bekleyin. Bu işlem birkaç saniye sürebilir.</p>
        </div>
        <div id="kc-form">
            <div id="kc-form-wrapper">
                <p class="info-text">
                    Onay işlemi için mobil imza sağlayıcısından bilgi bekleniyor. Bu işlem biraz zaman alabilir.
                </p>

                <form id="kc-form-login" action="${actionUrl!'/'}" method="post">
                    <div class="waiting-icon">
                        <img src="${url.resourcesPath}/img/wait.png" alt="Waiting" class="waiting-image">
                    </div>

                    <div id="kc-form-buttons">
                        <button type="submit" class="btn-submit">Durumu Yeniden Kontrol Et</button>
                    </div>
                </form>
            </div>
        </div>

        <#-- Footer -->
        <div id="kc-footer">
            <div id="footer-content">
                <span>© 2024 Mobil İmza Servisi. Tüm hakları saklıdır.</span>
                <a href="#">Gizlilik Politikası</a> |
                <a href="#">Kullanım Şartları</a> |
                <a href="#">İletişim</a>
            </div>
        </div>
    </#if>
    <style>
        .info-text {
            text-align: center;
            margin-bottom: 20px;
            font-size: 14px;
            color: #555;
        }

        .waiting-icon {
            text-align: center;
            margin: 20px 0;
        }

        .waiting-image {
            width: 80px;
            height: 80px;
            animation: spin 2s linear infinite;
        }

        @keyframes spin {
            0% { transform: rotate(0deg); }
            100% { transform: rotate(360deg); }
        }

        .btn-submit {
            background-color: #4CAF50;
            color: #fff;
            padding: 10px 20px;
            border: none;
            cursor: pointer;
            text-align: center;
        }

        .btn-submit:hover {
            background-color: #388E3C;
        }

        #kc-footer {
            margin-top: 20px;
            text-align: center;
            font-size: 12px;
            color: #777;
        }

        #kc-footer a {
            color: #007BFF;
            text-decoration: none;
        }

        #kc-footer a:hover {
            text-decoration: underline;
        }

        #kc-headerr {
            text-align: center;
            margin-bottom: 20px;
        }

        #kc-headerr .logo {
            width: 50px;
            margin-bottom: 10px;
        }

        #kc-headerr h1 {
            font-size: 22px;
            color: #333;
            margin: 0;
            font-weight: bold;
        }

        #kc-headerr p {
            font-size: 12px;
            color: #666;
            margin-top: 5px;
        }
        #kc-attempted-username {
            display: none;
        }

        #reset-login{
            display: none;
        }
    </style>

</@layout.registrationLayout>
