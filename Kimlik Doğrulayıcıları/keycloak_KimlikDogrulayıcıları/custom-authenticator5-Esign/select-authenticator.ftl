<#-- Import template and stylesheet -->
<#import "template.ftl" as layout>
<link rel="stylesheet" href="${url.resourcesPath}/login/style.css">

<@layout.registrationLayout displayMessage=false displayInfo=false; section>

    <#-- Header Section -->
    <#if section == "header">
        <div id="kc-header">
            <img src="${url.resourcesPath}/img/logo.png" alt="Keycloak Logo" class="logo">
            <h1>Giriş Yöntemini Seçin</h1>
            <p>Kullanıcı adı, şifre veya alternatif yöntemlerle giriş yapabilirsiniz.</p>
        </div>
    <#elseif section == "form">

    <div id="kc-form">
        <div id="kc-form-wrapper">
            <p class="info-text">
                Lütfen kimliğinizi doğrulamak için bir giriş yöntemini seçin:
            </p>
<#if authenticators?? && authenticators?size > 0>
            <div class="provider-buttons">
                <#list authenticators as auth>
                    <div class="provider-option">
                        <a href="${auth.authUrl}" class="provider-link">
                            <div class="provider-icon">
                                <img src="${url.resourcesPath}/img/${auth.displayName}.png" alt="${auth.displayName}">
                            </div>
                            <div class="provider-text">
                                <h2>${auth.displayName}</h2>
                                <p>${auth.helpText}</p>
                            </div>
                        </a>
                    </div>
                </#list>
            </div>
        </div>
    </div>
<#else>
    <p>Henüz kullanılabilir bir giriş yöntemi eklenmedi.</p>
</#if>
    <#-- Footer Section -->
    <div id="kc-footer">
        <div id="footer-content">
            <span>© 2024 Giriş Sistemi. Tüm hakları saklıdır.</span>
            <a href="#">Gizlilik Politikası</a> |
            <a href="#">Kullanım Şartları</a> |
            <a href="#">İletişim</a>
        </div>
    </div>

    </#if>

    <style>
        /* Genel Stil */
        #kc-header {
            text-align: center;
            padding: 20px 0;
            background-color: #f5f5f5;
            border-bottom: 1px solid #ddd;
        }

        #kc-header .logo {
            width: 60px;
            margin-bottom: 10px;
        }

        #kc-header h1 {
            font-size: 24px;
            color: #333;
            margin: 0;
        }

        #kc-header p {
            font-size: 14px;
            color: #666;
            margin-top: 5px;
        }

        #kc-form-wrapper {
            max-width: 600px;
            margin: 0 auto;
            padding: 20px;
        }

        .info-text {
            text-align: center;
            font-size: 16px;
            color: #555;
            margin-bottom: 20px;
        }

        .provider-buttons {
            display: flex;
            flex-wrap: wrap;
            justify-content: center;
            gap: 20px;
        }

        .provider-option {
            width: 250px;
            background: #fff;
            border: 1px solid #ddd;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            transition: all 0.3s ease;
            text-align: center;
        }

        .provider-option:hover {
            transform: translateY(-5px);
            box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
        }

        .provider-link {
            display: block;
            text-decoration: none;
            color: inherit;
            padding: 20px;
        }

        .provider-icon img {
            width: 50px;
            height: 50px;
            margin-bottom: 10px;
        }

        .provider-text h2 {
            font-size: 18px;
            color: #333;
            margin: 0;
        }

        .provider-text p {
            font-size: 14px;
            color: #777;
            margin-top: 5px;
        }

        #kc-footer {
            margin-top: 30px;
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
    </style>
</@layout.registrationLayout>
