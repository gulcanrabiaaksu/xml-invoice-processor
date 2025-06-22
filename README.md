# XML Invoice Processor

Bu proje, Base64 formatında gönderilen XML faturaları Spring Boot ile işleyip veritabanına kaydeden bir REST servisidir.

## Özellikler

- `POST /api/invoices` endpoint'i ile XML fatura gönderimi
- Base64 decode işlemi
- JAXB ile XML'den Java nesnesine dönüşüm
- Validasyon (invoiceNumber, customer.name boş olamaz, totalAmount > 0)
- H2 veritabanına kayıt (Spring Data JPA)
- Swagger UI ile API dokümantasyonu
- Basit unit testler

## Teknolojiler

- Java 17
- Spring Boot 3.x
- Spring Web
- Spring Data JPA
- H2 Database
- JAXB
- Swagger (Springdoc OpenAPI)

## Çalıştırma

1. Projeyi klonla:
    ```bash
    git clone https://github.com/gulcanrabiaaksu/xml-invoice-processor.git
    cd xml-invoice-processor
    ```

2. Projeyi derle:
    ```bash
    mvn clean install
    ```

3. Uygulamayı başlat:
    ```bash
    mvn spring-boot:run
    ```

4. Testleri çalıştırmak için:
    ```bash
    ./mvnw clean test
    ```

## API Dokümantasyonu

Swagger UI'ye şu adresten ulaşabilirsiniz:  
[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## Örnek API Kullanımı

**POST /api/invoices**

İstek gövdesi:
```json
{
  "base64xml": "PEludm9pY2U+PEludm9pY2VOdW1iZXI+SU5WLTEwMDE8L0ludm9pY2VOdW1iZXI+PElzc3VlRGF0ZT4yMDI1LTA2LTIwPC9Jc3N1ZURhdGU+PEN1c3RvbWVyPjxOYW1lPkFjbWUgQ29ycC48L05hbWU+PFRheE51bWJlcj4xMjM0NTY3ODkwPC9UYXhOdW1iZXI+PC9DdXN0b21lcj48VG90YWxBbW91bnQ+MTI1MC41MDwvVG90YWxBbW91bnQ+PEN1cnJlbmN5PlRSWTwvQ3VycmVuY3k+PC9JbnZvaWNlPg=="
}
