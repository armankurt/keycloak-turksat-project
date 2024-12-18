<#-- Import template and stylesheet -->
<#import "template.ftl" as layout>
<link rel="stylesheet" href="${url.resourcesPath}/login/style.css">

<@layout.registrationLayout displayMessage=messagesPerField.existsError('mobile_number') || messagesPerField.existsError('tcNumber') displayInfo=false; section>

    <#-- Header Section -->
    <#if section == "header">
        <div id="kc-header">
            <img src="${url.resourcesPath}/img/img.png" alt="Keycloak Logo" class="logo">
            <h1>Mobil İmza Girişi</h1>
            <p>Mobil İmzanız ile Güvenli Giriş</p>
        </div>
    <#elseif section == "form">

        <div id="kc-headerr">
            <img src="${url.resourcesPath}/img/img.png" alt="Keycloak Logo" class="logo">
            <h1>Mobil İmza Girişi</h1>
            <p>Mobil İmzanız ile Güvenli Giriş</p>
        </div>
        <div id="kc-form">
            <div id="kc-form-wrapper">
                <p class="info-text">
                    Telefon numaranız, T.C. kimlik numaranız ve operatör bilgilerinizi kullanarak mobil imzanızla giriş yapabilirsiniz.
                </p>

                <form id="kc-form-login" action="${actionUrl}" method="post">
                    <div class="input-group">
                        <label for="mobile_number">Telefon Numarası</label>
                        <input id="mobile_number" name="mobile_number" type="text" autocomplete="off" required />
                        <#if messagesPerField.existsError('mobile_number')>
                            <span class="input-error">${kcSanitize(messagesPerField.getFirstError('mobile_number'))?no_esc}</span>
                        </#if>
                    </div>

                    <div class="input-group">
                        <label for="tcNumber">T.C. Kimlik Numarası</label>
                        <input id="tcNumber" name="tcNumber" type="text" autocomplete="off" required />
                        <#if messagesPerField.existsError('tcNumber')>
                            <span class="input-error">${kcSanitize(messagesPerField.getFirstError('tcNumber'))?no_esc}</span>
                        </#if>
                    </div>

                    <div class="input-group operator-group">
                        <label for="operator">Operatör Seçiniz</label>
                        <div class="radio-buttons">
                            <label class="radio-option">
                                <input type="radio" name="operator" value="turkcell" required>
                                <div class="radio-label">
                                    <img src="${url.resourcesPath}/img/turkcell.png" alt="Turkcell">
                                    <span>Turkcell</span>
                                </div>
                            </label>
                            <label class="radio-option">
                                <input type="radio" name="operator" value="vodafone" required>
                                <div class="radio-label">
                                    <img src="${url.resourcesPath}/img/vodafone.png" alt="Vodafone">
                                    <span>Vodafone</span>
                                </div>
                            </label>
                            <label class="radio-option">
                                <input type="radio" name="operator" value="türk telekom" required>
                                <div class="radio-label">
                                    <img src="${url.resourcesPath}/img/tt.png" alt="Türk Telekom">
                                    <span>Türk Telekom</span>
                                </div>
                            </label>
                        </div>
                        <#if messagesPerField.existsError('operator')>
                            <span class="input-error">${kcSanitize(messagesPerField.getFirstError('operator'))?no_esc}</span>
                        </#if>
                    </div>

                    <div id="kc-form-buttons">
                        <button type="button" class="btn-cancel" onclick="window.location.href='${url.loginAction}'">Geri Dön</button>
                        <input class="btn-submit" name="login" id="kc-login" type="submit" value="Giriş Yap" />
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

        .input-group {
            margin-bottom: 15px;
        }

        .btn-cancel {
            background-color: #f44336;
            color: #fff;
            padding: 10px 20px;
            border: none;
            cursor: pointer;
            margin-right: 10px;
        }

        .btn-cancel:hover {
            background-color: #d32f2f;
        }

        .btn-submit {
            background-color: #4CAF50;
            color: #fff;
            padding: 10px 20px;
            border: none;
            cursor: pointer;
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
        #try-another-way {
            margin-top: 10px;
            text-align: center;
            color: #333;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .operator-group {
            text-align: center;
            margin-bottom: 20px;
        }

        .radio-buttons {
            display: flex;
            justify-content: center;
            gap: 20px;
        }

        .radio-option {
            display: flex;
            flex-direction: column;
            align-items: center;
            cursor: pointer;
        }

        .radio-option input[type="radio"] {
            display: none;
        }

        .radio-option .radio-label {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            width: 120px; /* Tüm kutuların sabit genişliği */
            height: 120px; /* Tüm kutuların sabit yüksekliği */
            padding: 10px;
            border: 2px solid transparent;
            border-radius: 10px;
            transition: all 0.3s ease;
            box-sizing: border-box; /* Padding dış sınırları etkilemesin */
        }

        .radio-option .radio-label span {
            font-size: 14px;
            font-weight: bold;
            color: #555;
        }

        .radio-option input[type="radio"]:checked + .radio-label {
            border-color: #4CAF50;
            background-color: #e8f5e9;
            color: #4CAF50;
        }

        .radio-option:hover .radio-label {
            border-color: #388E3C;
        }

        .input-error {
            color: #f44336;
            font-size: 12px;
            margin-top: 5px;
            display: block;
        }

        .radio-option .radio-label img {
                width: 50px;
                height: 50px;
                object-fit: contain;
                margin-bottom:5px;
        }
        /* Logo ve Başlık */
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

    </style>

</@layout.registrationLayout>
