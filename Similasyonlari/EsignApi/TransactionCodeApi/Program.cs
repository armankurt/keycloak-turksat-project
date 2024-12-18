using System;

var builder = WebApplication.CreateBuilder(args);

// Kestrel yapılandırması: tüm IP adreslerinden bağlantıyı dinle
builder.WebHost.ConfigureKestrel(options =>
{
    options.ListenAnyIP(7248); // 7248 portunu tüm IP adreslerinden dinle
});

// Servisleri ekle
builder.Services.AddControllers(); // Controller desteğini ekle

// Swagger API dokümantasyonu ekleme
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

// Session desteği ve MemoryCache yapılandırması
builder.Services.AddDistributedMemoryCache();
builder.Services.AddSession(options =>
{
    options.IdleTimeout = TimeSpan.FromMinutes(30); // Oturum süresi 30 dakika
    options.Cookie.HttpOnly = true; // Çerezlerin yalnızca HTTP üzerinden erişilebilir olması
    options.Cookie.IsEssential = true; // GDPR uyumluluğu için gerekli
});

// IMemoryCache servisini ekle (Bellek tabanlı cache desteği)
builder.Services.AddMemoryCache();

// CORS politikası ekle
builder.Services.AddCors(options =>
{
    options.AddPolicy("AllowAll", policy =>
    {
        policy.AllowAnyOrigin()   // Tüm kaynaklara izin ver
              .AllowAnyMethod()   // Tüm HTTP metodlarına izin ver (GET, POST, PUT vs.)
              .AllowAnyHeader();  // Tüm başlıklara izin ver
    });
});

var app = builder.Build();

// Geliştirme ortamında Swagger etkinleştir
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

// Middleware yapılandırmaları
app.UseCors("AllowAll");  // CORS'u etkinleştir
app.UseSession();         // Oturum desteğini etkinleştir
//app.UseAuthorization();   // Yetkilendirme middleware

// Controller'ları eşle
app.MapControllers();

// Uygulamayı başlat
app.Run();
