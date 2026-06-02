# Sistemi i Menaxhimit të Studentëve

**Autor:** Andis Ramja

Aplikacion Spring Boot me arkitekturë Maven multi-modul për menaxhimin e studentëve,
i ndërtuar si projekt për detyrë kursi universiteti. Përfshin operacionet CRUD,
eksportimin e të dhënave në Excel, pipeline CI/CD me Jenkins dhe deploy në OpenShift.

---

## Përshkrimi i Projektit

Ky aplikacion mundëson menaxhimin e plotë të të dhënave të studentëve nëpërmjet një
REST API-je. Funksionalitetet kryesore janë:

- **Regjistrimi** i një studenti të ri.
- **Listimi** i të gjithë studentëve.
- **Marrja** e një studenti sipas identifikuesit (ID).
- **Përditësimi** i të dhënave të një studenti.
- **Fshirja** e një studenti.
- **Eksportimi** i listës së studentëve në një file Excel (`.xlsx`).

Aplikacioni ndjek një ndarje të qartë në shtresa (model, dto, mapper, repository,
service, web, excel), duke respektuar parimet e arkitekturës së pastër dhe duke
lehtësuar testimin e mirëmbajtjen.

---

## Teknologjitë e Përdorura

| Teknologjia | Roli |
|-------------|------|
| **Java 17** | Gjuha e programimit |
| **Maven (multi-modul)** | Mjeti i ndërtimit dhe menaxhimit të varësive |
| **Spring Boot 3.3** | Korniza për REST API dhe injektimin e varësive |
| **Spring Data JPA** | Qasja në bazën e të dhënave |
| **H2 Database** | Bazë të dhënash in-memory për zhvillim |
| **PostgreSQL** | Bazë të dhënash për produksion (e konfigurueshme) |
| **Lombok** | Reduktimi i kodit boilerplate |
| **MapStruct 1.6** | Mapimi automatik DTO ↔ Entity |
| **Apache POI 5.x** | Gjenerimi i file-ave Excel (.xlsx) |
| **JUnit 5 + Mockito** | Testimi unitar |
| **Jenkins** | Pipeline CI/CD |
| **OpenShift** | Platforma e deploy-imit |

---

## Si të Instalosh dhe Ekzekutosh

### Parakushtet

- **Java 17 ose më e re** (JDK)
- **Apache Maven 3.8+**

> ⚠️ Sigurohu që komanda `java -version` raporton versionin **17+** dhe që `mvn -version`
> funksionon përpara se të vazhdosh.

### 1. Ndërtimi i projektit

Nga dosja rrënjë `student-management/`:

```bash
mvn clean install
```

Kjo komandë ndërton të gjitha modulet, ekzekuton testet dhe gjeneron artifaktet `.jar`.

### 2. Ekzekutimi i aplikacionit

```bash
mvn spring-boot:run -pl web
```

Ose drejtpërdrejt nga file-i `.jar` i gjeneruar:

```bash
java -jar web/target/web-1.0.0.jar
```

Aplikacioni do të jetë i aksesueshëm në: **http://localhost:8080**

### 3. Konsola e bazës së të dhënave H2

Gjatë zhvillimit, konsola web e H2-së është e disponueshme në:

```
http://localhost:8080/h2-console
```

Të dhënat e lidhjes:
- **JDBC URL:** `jdbc:h2:mem:studentdb`
- **User:** `sa`
- **Password:** *(bosh)*

### 4. Ekzekutimi i testeve

```bash
mvn test
```

---

## Dokumentimi i API-t

Të gjitha rrugët fillojnë me prefiksin `/api/students`.

| Metoda | Rruga | Përshkrimi | Statusi i suksesit |
|--------|-------|------------|--------------------|
| `POST` | `/api/students` | Shton një student të ri | `201 Created` |
| `GET` | `/api/students` | Liston të gjithë studentët | `200 OK` |
| `GET` | `/api/students/{id}` | Merr një student sipas ID | `200 OK` |
| `PUT` | `/api/students/{id}` | Përditëson një student | `200 OK` |
| `DELETE` | `/api/students/{id}` | Fshin një student | `204 No Content` |
| `GET` | `/api/students/export` | Shkarkon listën në file `.xlsx` | `200 OK` |

### Shembull i trupit të kërkesës (JSON)

```json
{
  "firstName": "Andis",
  "lastName": "Ramja",
  "email": "andis.ramja@example.com",
  "dateOfBirth": "2000-05-15",
  "enrollmentDate": "2019-10-01",
  "gpa": 3.8,
  "major": "Inxhinieri Informatike"
}
```

### Shembuj me `curl`

```bash
# Shto një student
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{"firstName":"Andis","lastName":"Ramja","email":"andis.ramja@example.com","gpa":3.8,"major":"Inxhinieri Informatike"}'

# Listo të gjithë studentët
curl http://localhost:8080/api/students

# Shkarko file-in Excel
curl -OJ http://localhost:8080/api/students/export
```

### Shembuj me PowerShell (Windows)

```powershell
# Shto një student
$body = @{ firstName="Andis"; lastName="Ramja"; email="andis.ramja@example.com"; gpa=3.8; major="Inxhinieri Informatike" } | ConvertTo-Json
Invoke-RestMethod -Uri "http://localhost:8080/api/students" -Method Post -Body $body -ContentType "application/json"

# Listo studentët
Invoke-RestMethod -Uri "http://localhost:8080/api/students" -Method Get

# Shkarko file-in Excel
Invoke-WebRequest -Uri "http://localhost:8080/api/students/export" -OutFile "studentet.xlsx"
```

### Trajtimi i gabimeve

API-ja kthen përgjigje të strukturuara gabimi me kodet përkatëse HTTP:

| Situata | Kodi HTTP |
|---------|-----------|
| Studenti nuk u gjet | `404 Not Found` |
| Email ekziston tashmë | `409 Conflict` |
| Të dhëna të pavlefshme | `400 Bad Request` |
| Gabim i brendshëm | `500 Internal Server Error` |

---

## Si të Konfigurosh Jenkins

Pipeline-i deklarativ ndodhet në file-in `Jenkinsfile` në rrënjë të projektit dhe
përmban 5 stage-t e mëposhtme:

1. **Marrja e Kodit** — merr kodin nga depoja (SCM).
2. **Ndërtimi** — `mvn clean install -DskipTests`.
3. **Testimi** — `mvn test`.
4. **Paketimi** — `mvn package`.
5. **Ruajtja Artifaktit** — ruan file-at `.jar`.

### Hapat e konfigurimit

1. Sigurohu që në Jenkins janë instaluar plugin-et **Pipeline** dhe **Git**.
2. Konfiguro një instalim Maven dhe një JDK 17 te **Manage Jenkins → Tools**
   (ose sigurohu që `mvn` dhe `java` janë në `PATH` të agjentit).
3. Krijo një **New Item → Pipeline**.
4. Te seksioni *Pipeline*, zgjidh **Pipeline script from SCM**.
5. Vendos URL-në e dep, degën dhe shtegun `Jenkinsfile`.
6. Ruaj dhe kliko **Build Now**.

Pas ndërtimit, artifaktet `.jar` do të jenë të disponueshme te seksioni
**Build Artifacts** i çdo ndërtimi.

---

## Deploy-imi në OpenShift

Manifestet ndodhen në dosjen `openshift/`:

- `deployment.yaml` — Deployment me 1 replikë dhe kufij burimesh.
- `service.yaml` — Service i tipit ClusterIP në portin 8080.
- `route.yaml` — Route për akses të jashtëm (URL publike).

### Hapat e deploy-imit

```bash
# 1. Hyr në OpenShift dhe zgjidh projektin (namespace)
oc login <URL-E-CLUSTER-IT>
oc project <NAMESPACE>

# 2. Ndërto imazhin (p.sh. me build nga burimi ose Docker)
oc new-build --name=student-management --binary --strategy=docker
oc start-build student-management --from-dir=. --follow

# 3. Zëvendëso NAMESPACE në deployment.yaml me emrin e projektit tënd,
#    pastaj apliko manifestet
oc apply -f openshift/deployment.yaml
oc apply -f openshift/service.yaml
oc apply -f openshift/route.yaml

# 4. Merr URL-në publike të aplikacionit
oc get route student-management
```

> Imazhi i përdorur është:
> `image-registry.openshift-image-registry.svc:5000/NAMESPACE/student-management:latest`
> Zëvendëso `NAMESPACE` me emrin e projektit tënd në OpenShift.

Probe-t e shëndetit (readiness/liveness) përdorin endpoint-in `/actuator/health`.

---

## Struktura e Projektit

```
student-management/
├── pom.xml                  # POM prind me menaxhimin e varësive
├── Jenkinsfile              # Pipeline CI/CD (5 stage-t)
├── Dockerfile               # Ndërtimi i imazhit për OpenShift
├── README.md                # Ky dokument
├── JIRA_SETUP.md            # Struktura Agile në Jira
├── openshift/               # Manifestet e deploy-imit
│   ├── deployment.yaml
│   ├── service.yaml
│   └── route.yaml
├── model/                   # Entiteti Student (JPA + Lombok)
├── dto/                     # StudentDTO me validim
├── mapper/                  # StudentMapper (MapStruct)
├── repository/              # StudentRepository (Spring Data JPA)
├── service/                 # StudentService + implementimi + testet
├── excel/                   # ExcelExportService (Apache POI)
└── web/                     # REST controller, trajtuesi i gabimeve, main
```

### Përgjegjësitë e moduleve

| Moduli | Përgjegjësia |
|--------|--------------|
| `model` | Entitetet JPA të sistemit |
| `dto` | Objektet e transferimit të të dhënave |
| `mapper` | Konvertimi DTO ↔ Entity me MapStruct |
| `repository` | Qasja në bazën e të dhënave |
| `service` | Logjika e biznesit (interface + implementim) |
| `excel` | Eksportimi në Excel me Apache POI |
| `web` | REST API, validimi dhe pika e nisjes |

---

*Projekt për detyrë kursi — Zhvilluar nga Andis Ramja.*
