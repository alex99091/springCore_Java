# springCore_Java
![Java17](http://img.shields.io/badge/java-17-blue.svg) ![SpringBoot](http://img.shields.io/badge/spring-boot-brightgreen.svg) ![Gradle](http://img.shields.io/badge/gradle-7.6.1-blue.svg) ![IntelliJ](http://img.shields.io/badge/intellij-idea-blue.svg)

### Why Spring?
- **기업용 자바 개발의 복잡성 감소**  
  - 기존 J2EE(EJB 기반)의 복잡한 설정과 무거운 구조를 단순화하기 위해.  
- **객체 지향 설계 지원**  
  - DI(의존성 주입)를 통해 코드의 재사용성과 테스트 용이성을 향상시키기 위해.  
- **효율적인 트랜잭션 관리**  
  - 선언적 트랜잭션을 지원하여 비즈니스 로직과 트랜잭션 관리를 분리하기 위해.  
- **AOP(공통 관심사 분리)**  
  - 로깅, 보안, 트랜잭션 관리 등을 핵심 비즈니스 로직과 분리하여 유지보수를 쉽게 하기 위해.  
- **경량 프레임워크 제공**  
  - 필요할 때만 필요한 기능을 사용하여 경량화된 애플리케이션을 만들기 위해.  

### Fundamentals
- **DI(Dependency Injection)**  
  - 객체 간의 의존성을 외부에서 주입하여 결합도를 낮춤.  
- **IoC(Inversion of Control)**  
  - 객체의 생성과 생명 주기를 컨테이너가 관리함.  
- **AOP(Aspect-Oriented Programming)**  
  - 공통 관심사(로깅, 보안, 트랜잭션 등)를 분리하여 코드의 중복을 제거.  
- **트랜잭션 관리**  
  - 선언적 트랜잭션을 제공하여 일관된 데이터 처리를 지원.  
- **Spring MVC**  
  - 웹 애플리케이션 개발을 위한 구조화된 아키텍처 제공.  
- **Spring Boot**  
  - 설정을 최소화하고 빠르게 애플리케이션을 개발할 수 있도록 지원.  


### **객체지향 5원칙 (SOLID)**
- **S - 단일 책임 원칙(Single Responsibility Principle, SRP)**  
  - 하나의 클래스는 하나의 역할만 수행해야 한다. 
```java
// 하나의 클래스는 하나의 역할만 수행해야 함
class ReportGenerator {
    public String generateReport() {
        return "Report Data";
    }
}

// 데이터 저장은 별도 클래스로 분리
class ReportSaver {
    public void saveToFile(String data) {
        System.out.println("Saving report: " + data);
    }
}
```
- **O - 개방-폐쇄 원칙(Open-Closed Principle, OCP)**  
  - 확장에는 열려 있고, 수정에는 닫혀 있어야 한다. (변경 없이 기능 확장 가능해야 함)  
```java
//  확장에는 열려 있고, 수정에는 닫혀 있어야 함
interface Payment {
    void pay(int amount);
}

class CreditCardPayment implements Payment {
    public void pay(int amount) {
        System.out.println("Paid with Credit Card: " + amount);
    }
}

class PayPalPayment implements Payment {
    public void pay(int amount) {
        System.out.println("Paid with PayPal: " + amount);
    }
}
// 기존 코드를 수정하지 않고 새로운 결제 방식 추가 가능
```
- **L - 리스코프 치환 원칙(Liskov Substitution Principle, LSP)**  
  - 자식 클래스는 부모 클래스를 대체할 수 있어야 한다. (다형성 유지)
```java
// 자식 클래스는 부모 클래스를 대체할 수 있어야 함
abstract class Bird {
    abstract void fly();
}

class Sparrow extends Bird {
    void fly() {
        System.out.println("Sparrow flying...");
    }
}

// 펭귄은 날 수 없으므로 LSP를 위반함 (잘못된 예시)
class Penguin extends Bird {
    void fly() {
        throw new UnsupportedOperationException("Penguins can't fly");
    }
}
```
- **I - 인터페이스 분리 원칙(Interface Segregation Principle, ISP)**  
  - 클라이언트가 자신이 사용하지 않는 메서드에 의존하지 않도록 인터페이스를 분리해야 한다.  
```java
// 사용하지 않는 메서드가 포함되지 않도록 인터페이스를 분리해야 함
interface Printer {
    void print();
}

interface Scanner {
    void scan();
}

class MultiFunctionPrinter implements Printer, Scanner {
    public void print() {
        System.out.println("Printing...");
    }

    public void scan() {
        System.out.println("Scanning...");
    }
}

// 단순 프린터는 Scanner 기능을 구현할 필요 없음
class SimplePrinter implements Printer {
    public void print() {
        System.out.println("Printing...");
    }
}
```
- **D - 의존성 역전 원칙(Dependency Inversion Principle, DIP)**  
  - 고수준 모듈이 저수준 모듈에 의존하면 안 되고, 추상화에 의존해야 한다.  
```java
// 고수준 모듈이 저수준 모듈에 의존하면 안 되고, 추상화에 의존해야 함
interface Notification {
    void sendMessage(String message);
}

class EmailNotification implements Notification {
    public void sendMessage(String message) {
        System.out.println("Email sent: " + message);
    }
}

// High-level 모듈이 Low-level 모듈(EmailNotification)에 직접 의존하지 않도록 함
class NotificationService {
    private final Notification notification;

    public NotificationService(Notification notification) {
        this.notification = notification;
    }

    public void notifyUser(String message) {
        notification.sendMessage(message);
    }
}

// Dependency Injection을 통해 객체 주입
class Main {
    public static void main(String[] args) {
        Notification emailNotification = new EmailNotification();
        NotificationService service = new NotificationService(emailNotification);
        service.notifyUser("Hello, Dependency Inversion!");
    }
}
```

즉, 스프링은 DI로 객체 간 결합도를 낮춰 다형성을 유지하고,  
AOP로 공통 기능을 분리해 핵심 로직에 집중할 수 있도록 설계되었으며,  
이를 통해 유연한 구조와 변경 용이성을 제공하여, 레고 블록처럼 조립하며 개발할 수 있음.  
또한, SOLID 원칙을 적용하여 코드의 책임을 명확히 하고,  
유지보수성이 높으며 확장 가능한 시스템을 구축할 수 있음.  
