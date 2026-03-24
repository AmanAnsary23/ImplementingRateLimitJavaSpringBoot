# 🚦 Rate Limiter — Spring Boot

A Spring Boot REST API demonstrating **Fixed Window Counter** rate limiting algorithm using `ConcurrentHashMap` and `HandlerInterceptor` — restricts clients to **5 requests per minute** and returns `429 Too Many Requests` on limit exceeded.

---

## 🛠️ Tech Stack

- **Java 17**
- **Spring Boot 3.x**
- **Spring Web (MVC)**
- **ConcurrentHashMap** (in-memory storage)

---

## 📁 Project Structure

```
rate-limiter-demo/
├── src/main/java/com/example/ratelimiter/
│   ├── RateLimiterApplication.java       ← Entry point
│   ├── interceptor/
│   │   └── RateLimitInterceptor.java     ← Intercepts every request
│   ├── service/
│   │   └── LimiterService.java           ← Core rate limit logic
│   ├── config/
│   │   └── WebConfig.java                ← Registers interceptor
│   └── controller/
│       └── ProductController.java        ← REST API endpoints
└── pom.xml
```

---

## ⚙️ How It Works

Every incoming request is intercepted before reaching the controller:

```
Request → RateLimitInterceptor → LimiterService
                                       ↓
                               Check ConcurrentHashMap
                                ↙               ↘
                           Allowed ✅         Blocked ❌
                               ↓                  ↓
                           Controller         429 Response
```

### Fixed Window Counter Algorithm

```
Window = 1 minute
Limit  = 5 requests per IP

Request 1-5  →  200 OK ✅
Request 6    →  429 Too Many Requests ❌
After 1 min  →  Counter resets, requests allowed again ✅
```

---

## 🚀 Getting Started

### Prerequisites
- Java 17+
- Maven

### Run the project

```bash
git clone https://github.com/your-username/rate-limiter-demo.git
cd rate-limiter-demo
mvn spring-boot:run
```

Server starts at:
```
http://localhost:8080
```

---

## 🧪 Testing with Postman

**Endpoint:**
```
GET http://localhost:8080/api/products
```

| Request | Status | Response |
|---------|--------|----------|
| 1st - 5th | `200 OK` | `Here are your products!` |
| 6th onwards | `429 Too Many Requests` | `Rate limit exceeded. Try after 1 minute.` |

**Quick Test — Use Postman Collection Runner:**
1. Save request in a Collection
2. Click **Run Collection**
3. Set iterations → `6`
4. Set delay → `100ms`
5. Hit **Run** 🚀

---

## 📦 Dependencies

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

---

## 📌 Key Concepts Used

| Concept | Usage |
|---------|-------|
| `HandlerInterceptor` | Intercepts requests before controller |
| `WebMvcConfigurer` | Registers interceptor for `/api/**` routes |
| `ConcurrentHashMap` | Thread-safe in-memory request counter |
| `Fixed Window Counter` | Rate limiting algorithm |
| `429 Too Many Requests` | Standard HTTP status for rate limit exceeded |

---

## 🔮 Future Improvements

- [ ] Add Redis for distributed rate limiting
- [ ] Implement Sliding Window algorithm
- [ ] Add per-user rate limits (JWT based)
- [ ] Expose rate limit headers (`X-RateLimit-Remaining`)

---

## 👨‍💻 Author

**Aman**  
Backend Developer | Spring Boot | Node.js  
[GitHub](https://github.com/AmanAnsary23) • [LinkedIn](https://linkedin.com/in/aman-ansary)
```
