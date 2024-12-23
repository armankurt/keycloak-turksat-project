# Keycloak Kurulumu ve Özelleştirme Talimatları

Bu proje, keycloakta modern kimlik doğrulama ve yetkilendirme işlemleri için kullanılan, açık kaynaklı bir kimlik sağlayıcıdır. Bu belge, Docker ortamında Keycloak konteynırının kurulumu ve özelleştirilmesi için gereken tüm adımları detaylı bir şekilde açıklamaktadır. Ayrıca, özelleştirilmiş doğrulayıcıların (authenticator) oluşturulması, bu doğrulayıcıların Keycloak üzerine yüklenmesi ve doğru bir şekilde yapılandırılması konularında rehberlik etmektedir.

Bu belge, özellikle kullanıcıların kimlik doğrulama sürecini kişiselleştirmek ve sistem gereksinimlerine uygun hale getirmek isteyen yazılım geliştiriciler için hazırlanmıştır. Örneğin, bir projede kullanıcıların TC Kimlik Numarası ile giriş yapabilmesi, e-posta veya SMS yoluyla iki faktörlü doğrulama gerçekleştirebilmesi ya da mobil imza ve e-imza gibi ileri düzey güvenlik yöntemlerini kullanabilmesi gerekebilir. Burada ele alınan yöntemler, bu gibi ihtiyaçlara yanıt verecek şekilde tasarlanmıştır.

Belgede ayrıca Keycloak temasının özelleştirilmesine ilişkin bilgiler de bulunmaktadır. Özel bir giriş sayfası tasarlamak, şirket markasına uygun bir görünüm ve his yaratmak isteyen projeler için özel tema oluşturma ve düzenleme adımları detaylandırılmıştır. Keycloak’ı kullanarak kimlik doğrulama süreçlerinizi hem güvenli hem de kullanıcı dostu bir hale getirmek için ihtiyacınız olan tüm teknik bilgiler bu belgede yer almaktadır.

---

## Docker ile Keycloak Kurulumu

Docker üzerinde Keycloak konteynırını oluşturmak ve başlatmak için aşağıdaki komutları takip ediniz:

```bash
docker run -d --name keycloak -p 8080:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin123 quay.io/keycloak/keycloak:latest start-dev
```

Bu işlem tamamlandığında Keycloak arayüzüne tarayıcınız üzerinden [http://localhost:8080](http://localhost:8080) adresinden erişebilirsiniz. Yönetici hesabı olarak giriş yapmak için yukarıdaki komutta belirttiğiniz kullanıcı adı ve şifreyi kullanınız.

---

## 1. Keycloak Özelleştirilmiş Doğrulayıcıların Yüklenmesi ve Admin Konsoluna Eklenmesi

Bu bölümde Maven kullanarak özelleştirilmiş doğrulayıcıların (authenticator) nasıl oluşturulacağı, Docker üzerinden Keycloak konteynırına nasıl yükleneceği ve bu doğrulayıcıların Keycloak Admin Konsolu üzerinden nasıl yapılandırılacağı anlatılmaktadır.

### Maven ile Özelleştirilmiş Doğrulayıcıların Oluşturulması
Her bir doğrulayıcı için aşağıdaki adımları takip ederek Maven ile jar dosyasını oluşturun ve Keycloak konteynırına yükleyin.

#### 1.1 Doğrulayıcı: TcKimlikGiris
Bu doğrulayıcı, kullanıcıların TC Kimlik Numarası ile giriş yapmalarını sağlar.

```bash
cd /Users/armanyilmazkurt/Desktop/keycloak/custom-authenticator1-TcKimlikGiris/
mvn clean install
docker cp /Users/armanyilmazkurt/Desktop/keycloak/custom-authenticator1-TcKimlikGiris/target/TcKimlikGiris.jar keycloak:/opt/keycloak/providers/
docker restart keycloak
```

#### 1.2 Doğrulayıcı: 2FaktörDoğrulamaMetotSecimi
Bu doğrulayıcı, kullanıcının iki faktörlü doğrulama yöntemini seçmesini sağlar (SMS, e-posta veya uygulama).

```bash
cd /Users/armanyilmazkurt/Desktop/keycloak/custom-authenticator2-2FaktörDog̈rulamaMetotSecimi/
mvn clean install
docker cp /Users/armanyilmazkurt/Desktop/keycloak/custom-authenticator2-2FaktörDog̈rulamaMetotSecimi/target/MetotSecimi.jar keycloak:/opt/keycloak/providers/
docker restart keycloak
```

#### 1.3 Doğrulayıcı: 2FactorEmail
Bu doğrulayıcı, e-posta üzerinden iki faktörlü doğrulama kodu göndermeyi sağlar.

```bash
cd /Users/armanyilmazkurt/Desktop/keycloak/custom-authenticator3-2FactorEmail/
mvn clean install
docker cp /Users/armanyilmazkurt/Desktop/keycloak/custom-authenticator3-2FactorEmail/target/2FactorEmail.jar keycloak:/opt/keycloak/providers/
docker restart keycloak
```

#### 1.4 Doğrulayıcı: MobileSign
Bu doğrulayıcı, mobil imza üzerinden kimlik doğrulama yapılmasını sağlar.

```bash
cd /Users/armanyilmazkurt/Desktop/keycloak/custom-authenticator4-MobileSign/
mvn clean install
docker cp /Users/armanyilmazkurt/Desktop/keycloak/custom-authenticator4-MobileSign/target/MobileSign.jar keycloak:/opt/keycloak/providers/
docker restart keycloak
```

#### 1.5 Doğrulayıcı: Esign
Bu doğrulayıcı, e-imza kullanarak doğrulama işlemlerinin gerçekleştirilmesini sağlar.

```bash
cd /Users/armanyilmazkurt/Desktop/keycloak/custom-authenticator5-Esign/
mvn clean install
docker cp /Users/armanyilmazkurt/Desktop/keycloak/custom-authenticator5-Esign/target/Esign.jar keycloak:/opt/keycloak/providers/
docker restart keycloak
```

#### 1.6 Doğrulayıcı: 2FactorSMS
Bu doğrulayıcı, SMS yoluyla iki faktörlü doğrulama kodu göndermeyi sağlar.

```bash
cd /Users/armanyilmazkurt/Desktop/keycloak/custom-authenticator6-keycloak-2fa-sms-authenticator-main/
mvn clean install
docker cp /Users/armanyilmazkurt/Desktop/keycloak/custom-authenticator6-keycloak-2fa-sms-authenticator-main/target/2FactorSMS.jar keycloak:/opt/keycloak/providers/
docker restart keycloak
```

# Keycloak Admin Konsolunda Doğrulayıcıları Yapılandırma

Keycloak Admin Konsolu üzerinden yüklenmiş doğrulayıcıların yapılandırılması için aşağıdaki adımları takip ediniz.

---

## Adım 1: Keycloak Admin Konsolu'na Giriş

1. Tarayıcınız üzerinden [http://localhost:8080](http://localhost:8080) adresine gidin.
2. Yönetici hesabı (username: **admin**, password: **admin123**) ile giriş yapın.

---

## Adım 2: Yeni Bir Authentication Flow Oluşturma

1. Sol menüden **Authentication** sekmesine gidin.
2. **Flows** sekmesini açın.
3. Sayfanın üst kısmındaki **Add flow** butonuna tıklayın.
4. Yeni akışa bir isim verin (mesela **MyBrowserFlow**) ve **Save** butonuna tıklayarak kaydedin.

---

## Adım 3: Adımları Ekleyerek Doğrulayıcıları Yapılandırma

Yeni oluşturulan akışa doğrulayıcıları eklemek için:

1. **Add execution** butonuna tıklayarak adımları ekleyin.
2. Yüklediğiniz doğrulayıcıları (mesela, **TC Kimlik Authenticator**, **Email OTP Authenticator**, **SMS Authenticator**) sırayla seçin ve ekleyin.
3. Eklediğiniz her adımın **Requirement** ayarını yapılandırın:
   - **Required**: Adım zorunlu olarak çalışır.
   - **Alternative**: Kullanıcı alternatif doğrulama yöntemlerinden birini seçebilir.

### Örnek Yapılandırma

Aşağıdaki gibi ağaç yapısında bir sıralama oluşturun:

```
MyBrowserFlow
│
├── Cookie (Alternative)
├── Identity Provider Redirector (Alternative)
├── Username or Email ile Giriş (Alternative)
│   ├── Username Password Form (Required)
│   └── Method Selection Authenticator (Required)
│       ├── OTP Form for Username/Password Form (Required)
│       ├── Email (Alternative)
│		├── Email OTP (Required)
│       ├── SMS (Alternative)
│		├── SMS Authenticator (Required)
│       └── TOTP (Disabled)
│		├── OTP Form (Required)
│
├── TC Kimlik ile Giriş (Alternative)
│   ├── TC Kimlik Authenticator (Required)
│   ├── Method Selection Authenticator (Required)
│   └── OTP Form TC Kimlik ile Giriş (Required)
│       ├── OTP Form for Username/Password Form (Required)
│       ├── Email (Alternative)
│		├── Email OTP (Required)
│       ├── SMS (Alternative)
│		├── SMS Authenticator (Required)
│       └── TOTP (Disabled)
│		├── OTP Form (Required)
│
├── Mobile Imza ile Giriş (Required)
│   ├── Mobile Sign Display(Required)
│   ├── Consent Form Authenticator (Required)
│   └── Waiting Form Authenticator (Required)
│
└── E-imza Giriş (Alternative)
    ├── Esign Authenticator (Required)
    └── E-sign Number Verification (Required)
```

---

## Adım 4: Yeni Akışı Kullanıma Almak

1. **Bindings** sekmesine gidin.
2. **Browser Flow** alanında, oluşturduğunuz yeni akışı (**MyBrowserFlow**) seçin.
3. **Save** butonuna tıklayarak değişiklikleri kaydedin.

# Keycloak Tema Oluşturma Rehberi

Bu rehber, Keycloak üzerinde özel bir tema oluşturmak, dosya yapısını düzenlemek ve temayı Keycloak Admin Konsolu üzerinden aktif hale getirmek için gerekli adımları detaylı bir şekilde açıklamaktadır.

---

## 2. Keycloak Konteynırına Erişim

Keycloak konteynırının içine erişim sağlamak için aşağıdaki komutu kullanın:

```bash
docker exec -it keycloak /bin/bash
```

Bu komut, Keycloak konteynırının terminaline bağlanmanızı sağlar. Buradan dosya yapısını inceleyebilir ve özelleştirilmiş tema klasörlerini oluşturabilirsiniz.

---

## 3. Özel Tema Klasörünün Oluşturulması

Keycloak temaları, `/opt/keycloak/themes/` dizini altında yer alır. Buraya yeni bir tema klasörü oluşturmanız gerekmektedir.

### Tema Yapısını Oluşturma

1. **Temalar** dizinine gidin:
   ```bash
   cd /opt/keycloak/themes/
   ```

2. **Yeni tema klasörünü oluşturun:**
   ```bash
   mkdir my-new-theme
   ```

3. **Tema alt klasörlerini oluşturun:** Tema için gerekli klasör yapısını aşağıdaki komutlarla oluşturabilirsiniz:
   ```bash
   mkdir -p my-new-theme/login/resources/img
   mkdir -p my-new-theme/common
   ```

### Tema Klasörü Yapısı

Oluşturduğunuz klasör yapısı şu şekilde görünmelidir:

```
/opt/keycloak/themes/my-new-theme
│
├── login
│   ├── resources
│       └── img
├── common
```

- **login**: Giriş sayfası için FTL dosyalarını içerecek klasör.
- **resources/img**: Temada kullanılacak resimler için klasör.
- **common**: Tema için ortak CSS ve JS dosyalarının yükleneceği klasör.

---

## 4. FTL ve Kaynak Dosyalarının Yüklenmesi

Özelleştirilmiş tema dosyalarınızı (FTL, CSS, resimler vb.) ilgili klasörlere yüklemeniz gerekmektedir. Bunun için konteynır dışında hazırladığınız dosyaları aşağıdaki komutlarla kopyalayabilirsiniz:

### FTL Dosyalarının Yüklenmesi

```bash
docker cp /Users/armanyilmazkurt/Desktop/keycloak_FTL-dosyalari/consent-form.ftl keycloak:/opt/keycloak/themes/my-new-theme/login/
docker cp /Users/armanyilmazkurt/Desktop/keycloak_FTL-dosyalari/login-config-totp.ftl keycloak:/opt/keycloak/themes/my-new-theme/login/
docker cp /Users/armanyilmazkurt/Desktop/keycloak_FTL-dosyalari/login-email-otp.ftl keycloak:/opt/keycloak/themes/my-new-theme/login/
docker cp /Users/armanyilmazkurt/Desktop/keycloak_FTL-dosyalari/login-esign-number.ftl keycloak:/opt/keycloak/themes/my-new-theme/login/
docker cp /Users/armanyilmazkurt/Desktop/keycloak_FTL-dosyalari/login-esign.ftl keycloak:/opt/keycloak/themes/my-new-theme/login/
docker cp /Users/armanyilmazkurt/Desktop/keycloak_FTL-dosyalari/login-method-selection.ftl keycloak:/opt/keycloak/themes/my-new-theme/login/
docker cp /Users/armanyilmazkurt/Desktop/keycloak_FTL-dosyalari/login-otp.ftl keycloak:/opt/keycloak/themes/my-new-theme/login/
docker cp /Users/armanyilmazkurt/Desktop/keycloak_FTL-dosyalari/login-reset-password.ftl keycloak:/opt/keycloak/themes/my-new-theme/login/
docker cp /Users/armanyilmazkurt/Desktop/keycloak_FTL-dosyalari/login-sms.ftl keycloak:/opt/keycloak/themes/my-new-theme/login/
docker cp /Users/armanyilmazkurt/Desktop/keycloak_FTL-dosyalari/login-tc.ftl keycloak:/opt/keycloak/themes/my-new-theme/login/
docker cp /Users/armanyilmazkurt/Desktop/keycloak_FTL-dosyalari/login.ftl keycloak:/opt/keycloak/themes/my-new-theme/login/
docker cp /Users/armanyilmazkurt/Desktop/keycloak_FTL-dosyalari/mobilsign-authentication.ftl keycloak:/opt/keycloak/themes/my-new-theme/login/
docker cp /Users/armanyilmazkurt/Desktop/keycloak_FTL-dosyalari/select-authenticator.ftl keycloak:/opt/keycloak/themes/my-new-theme/login/
docker cp /Users/armanyilmazkurt/Desktop/keycloak_FTL-dosyalari/waiting-form.ftl keycloak:/opt/keycloak/themes/my-new-theme/login/
```

### Resim Dosyalarının Yüklenmesi

```bash
docker cp /Users/armanyilmazkurt/Desktop/keycloak_Resimleri/img.png /opt/keycloak/themes/my-new-theme/login/resources/img
docker cp /Users/armanyilmazkurt/Desktop/keycloak_Resimleri/tt.png /opt/keycloak/themes/my-new-theme/login/resources/img
docker cp /Users/armanyilmazkurt/Desktop/keycloak_Resimleri/turkcell.png /opt/keycloak/themes/my-new-theme/login/resources/img
docker cp /Users/armanyilmazkurt/Desktop/keycloak_Resimleri/vodafone.png /opt/keycloak/themes/my-new-theme/login/resources/img
docker cp /Users/armanyilmazkurt/Desktop/keycloak_Resimleri/wait.png /opt/keycloak/themes/my-new-theme/login/resources/img
```

---

## 5. Temayı Aktif Hale Getirme

Özel temayı aktif hale getirmek için Keycloak Admin Konsolu kullanın.

### Adımlar:

1. **Keycloak Admin Konsolu'na erişim sağlayın:**
   Tarayıcınızdan [http://localhost:8080](http://localhost:8080) adresine giderek yönetici hesabı ile giriş yapın.

2. **Realm Ayarlarına gidin:**
   Sol menüden **Realm Settings** (Realm Ayarları) seçeneğine tıklayın.

3. **Temayı seçin:**
   **Themes** sekmesine geçin. **Login Theme** (Giriş Teması) alanında, oluşturduğunuz temayı (**my-new-theme**) seçin.

4. **Değişiklikleri kaydedin:**
   Sayfanın altında bulunan **Save** butonuna tıklayarak değişiklikleri kaydedin.

---

## 6. Keycloak Konteynırını Yeniden Başlatma

Tema yüklemelerini tamamladıktan sonra Keycloak konteynırını yeniden başlatmanız gerekmektedir:

```bash
docker restart keycloak
```

Bu işlem, yeni temanın Keycloak tarafından tanınmasını sağlar.

---

## Logların İncelenmesi

Konteynırın loglarını izlemek ve olası hataları tespit etmek için şu komutu kullanabilirsiniz:

```bash
docker logs -f keycloak
```
---
## 7. Keycloak E-İmza ve Mobil İmza Simülasyonu Çalıştırma Talimatları

Bu bölüm, e-İmza ve mobil imza simülasyonlarının nasıl çalıştırılacağını detaylı bir şekilde açıklamaktadır. Aşağıdaki adımları takip ederek her iki simülasyonu test edebilir ve doğrulama süreçlerini uygulayabilirsiniz.

---

## 7.1 E-İmza Simülasyonu

### 1. Gereksinimler

- **esign-similasyon.html** dosyasını bir tarayıcıda çalıştırarak kullanıcı arayüzüne erişin.
- **EsignApi** içeren API servisini çalıştırmak için gerekli olan altyapıyı hazırlayın.

### 2. API Sunucusunu Başlatma

- API projesini çalıştırarak işlem kodlarının saklanmasını ve doğrulamasını sağlayın.
- **Program.cs** dosyasında portun **7248** olduğundan emin olun ve sunucuyu başlatın.

### 3. Kullanıcı Arayüzü İşlemleri

- **transactionCode** alanına Keycloak tarafından sağlanan işlem kodunu girin.
- **cardType** seçimini yapın (Net iD, Siemens CardOS gibi).
- "Doğrulamayı Kontrol Et" veya "Doğrulama Yap" butonlarını kullanarak işlem kodunun durumunu sorgulayın ve doğrulayın.

### 4. Yanıtlar

- **Kod doğrulandı! Giriş başarılı.**: İşlem kodu başarıyla doğrulandı.
- **Kod doğrulama bekleniyor.**: İşlem kodu doğrulama için bekleniyor.
- **Geçersiz kod!**: Hatalı işlem kodu girdiniz.

---

## 7.2 Mobil İmza Simülasyonu

### 1. Gereksinimler

- Python ile yazılmış **MobileSignApi.py** dosyasını kullanarak mobil imza doğrulama servisini başlatın.
- Mobil imza API’sinin çalıştığı port (**8081**) ve endpoint adreslerini not edin.

### 2. Mobil İmza Servisini Çalıştırma

**MobileSignApi.py** dosyasını çalıştırmak için:

```bash
python MobileSignApi.py
```

Sunucunun çalıştığından emin olmak için tarayıcınız veya Postman ile aşağıdaki endpoint'leri test edin.

### 3. API Endpoint'lerinin Kullanımı

#### Kullanıcı Onay Durumunu Sorgulama

- **Endpoint**: `/mobil-sign/approval`
- **Yöntem**: `GET`
- **Açıklama**: Kullanıcının onay durumunu kontrol eder.

**Yanıtlar**:

- `"status": "approved"`: Kullanıcı onaylanmış.
- `"status": "pending"`: Kullanıcı onay bekliyor.

**curl ile örnek:**

```bash
curl -X GET http://127.0.0.1:8081/mobil-sign/approval
```

#### Kullanıcıyı Onaylama

- **Endpoint**: `/mobil-sign/approve`
- **Yöntem**: `POST`
- **Açıklama**: Kullanıcıyı onaylamak için kullanılır.

**Gerekli JSON Body**:

```json
{
  "password": "1234"
}
```

**Yanıtlar**:

- `"message": "User approved"`: Kullanıcı başarıyla onaylandı.
- `"message": "Invalid password"`: Geçersiz şifre.

**curl ile örnek:**

```bash
curl -X POST http://127.0.0.1:8081/mobil-sign/approve \
-H "Content-Type: application/json" \
-d '{"password": "1234"}'
```

### 4. Kullanıcı İşlemleri

- Kullanıcı, "Mobil İmza Onay Bekleniyor" mesajını alana kadar `/mobil-sign/approval` endpoint’ini kullanarak durumu kontrol eder.
- Kullanıcı **password** alanını kullanarak `/mobil-sign/approve` endpoint’inden başarıyla onaylanır.

---

## 8. LDAP Entegrasyonu

LDAP (Lightweight Directory Access Protocol), kullanıcı kimlik bilgilerini merkezi bir dizin hizmetinde saklamak ve yönetmek için kullanılan bir protokoldür. Keycloak ile LDAP entegrasyonu sayesinde kullanıcılar, merkezi bir dizin üzerinde tanımlanan kimlik bilgileriyle giriş yapabilir. Bu entegrasyon hem kullanıcı yönetimini kolaylaştırır hem de güvenliği artırır.

### 1. LDAP Sunucusunun Ayarlanması

#### 1.1 Docker ile LDAP Sunucusunu Çalıştırma

- Yüklediğiniz **docker-compose.yml** dosyasına göre aşağıdaki adımları izleyerek bir LDAP sunucusu çalıştırabilirsiniz.

1. Docker Compose dosyasını bir dizine kaydedin.
2. Komut satırında aşağıdaki komutu çalıştırarak LDAP sunucusunu başlatın:

```bash
docker-compose up -d
```

- LDAP sunucusu, varsayılan olarak **389** portunda çalışacak.
- Sunucunun erişilebilir olduğundan emin olun.

#### 1.2 LDIF Dosyaları ile Verilerin Eklenmesi

**add\_ou.ldif** ve **users.ldif** dosyalarını kullanarak organizasyon birimlerini (OU) ve kullanıcıları LDAP dizinine ekleyebilirsiniz.

**ldapadd** komutuyla bu dosyaları eklemek için:

```bash
ldapadd -x -D "cn=admin,dc=example,dc=org" -w admin123 -f add_ou.ldif
ldapadd -x -D "cn=admin,dc=example,dc=org" -w admin123 -f users.ldif
```

---

### 2. Keycloak ile LDAP Entegrasyonu

#### 2.1 Keycloak LDAP Federasyonu Oluşturma

1. Keycloak Admin Konsoluna giriş yapın.
2. **Realm Settings** > **User Federation** sekmesine gidin.
3. **Add provider** seçeneğinden LDAP'ı seçin.

#### 2.2 LDAP Ayarları

- **Edit Mode**: `READ_ONLY` veya `WRITABLE` seçin (LDAP dizinindeki kullanıcıların Keycloak üzerinden düzenlenip düzenlenemeyeceğini belirler).
- **Vendor**: Kullanılan LDAP sunucusuna uygun seçimi yapın (Active Directory, OpenLDAP vb.).
- **Connection URL**: `ldap://localhost:389`
- **Bind DN**: `cn=admin,dc=example,dc=org`
- **Bind Credential**: LDAP admin şifresi (admin123 gibi).
- **User DN**: Kullanıcıların bulunduğu DN (`ou=users,dc=example,dc=org` gibi).

#### 2.3 LDAP Kullanıcılarının Keycloak'a Eşlenmesi

1. **Sync Settings** bölümünden LDAP kullanıcılarını Keycloak'a senkronize edin.
2. **Mapper** sekmesine giderek LDAP kullanıcı niteliklerini Keycloak niteliklerine eşleştirin (LDAP **cn**'ini Keycloak **username**'ine eşleme gibi).


---

## Önemli Not

Bu belgedeki komutlarda yer alan dosya yolları (`/Users/armanyilmazkurt/Desktop/...`) örnek olarak verilmiştir. Kendi sisteminizde bu yolları dosyalarınızın bulunduğu dizinlere göre düzenlemeyi unutmayınız. Özellikle farklı işletim sistemleri kullanıyorsanız (Windows gibi), dosya yollarını buna uygun biçimde değiştirmelisiniz.



