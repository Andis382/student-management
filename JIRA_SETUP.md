# Struktura e Projektit në Jira — Sistemi i Menaxhimit të Studentëve

**Autor:** Andis Ramja
**Metodologjia:** Agile (Scrum)
**Çelësi i propozuar i projektit:** `SMS`

---

## 1. Informacion i Përgjithshëm

| Fusha | Vlera |
|-------|-------|
| Emri i projektit | Sistemi i Menaxhimit të Studentëve |
| Çelësi i projektit | SMS |
| Tipi i projektit | Scrum |
| Numri i Epic-ave | 5 |
| Numri i Sprint-eve | 2 |
| Kohëzgjatja e një Sprint-i | 1 javë |

---

## 2. Krijimi i Projektit në Jira

1. Hyr në Jira dhe zgjidh **Create Project**.
2. Zgjidh shabllonin **Scrum**.
3. Vendos emrin *Sistemi i Menaxhimit të Studentëve* dhe çelësin *SMS*.
4. Aktivizo modulet **Backlog** dhe **Sprint Board**.
5. Krijo Epic-at, pastaj User Story-t nën to dhe në fund Task-et.

---

## 3. Hierarkia Agile

Puna organizohet në tri nivele:

- **Epik (Epic):** një njësi e madhe pune që përmban disa historira.
- **Historia e Përdoruesit (User Story):** një funksionalitet i prekshëm me vlerë për përdoruesin.
- **Detyrë (Task):** një hap teknik konkret brenda një historie.

---

## 4. Epikët, Historitë dhe Detyrat

### Epik 1: Konfigurimi i Projektit dhe Arkitektura
> Përgatitja e strukturës bazë të projektit dhe e mjedisit të zhvillimit.

- **Historia 1.1: Inicializimi i projektit Maven multi-modul**
  - Detyrë: Krijimi i POM-it prind me `packaging=pom`
  - Detyrë: Deklarimi i të 7 moduleve
  - *Kriter pranimi:* Komanda `mvn validate` përfundon pa gabime.
- **Historia 1.2: Konfigurimi i varësive në POM prind**
  - Detyrë: Shtimi i `dependencyManagement` për modulet e brendshme
  - Detyrë: Shtimi i versioneve për MapStruct dhe Apache POI
  - Detyrë: Konfigurimi i procesorëve të anotacioneve (Lombok + MapStruct)
- **Detyrë (e përgjithshme): Krijimi i strukturës së dosjeve**
  - Krijimi i drurit të dosjeve `model/dto/mapper/service/repository/web/excel`

### Epik 2: Shtresa e të Dhënave
> Modelimi i të dhënave dhe qasja në bazën e të dhënave.

- **Historia 2.1: Implementimi i entitetit Student me Lombok**
  - Detyrë: Përkufizimi i fushave (id, firstName, lastName, email, dateOfBirth, enrollmentDate, gpa, major)
  - Detyrë: Shtimi i anotacioneve JPA dhe Lombok
- **Historia 2.2: Krijimi i StudentDTO dhe mapper-it MapStruct**
  - Detyrë: Krijimi i klasës `StudentDTO` me anotacione validimi
  - Detyrë: Krijimi i interface-it `StudentMapper` me `componentModel="spring"`
- **Historia 2.3: Implementimi i StudentRepository**
  - Detyrë: Zgjerimi i `JpaRepository`
  - Detyrë: Shtimi i metodave `findByEmail` dhe `existsByEmail`

### Epik 3: Logjika e Biznesit dhe API
> Realizimi i operacioneve CRUD dhe ekspozimi i tyre përmes REST.

- **Historia 3.1: Implementimi i StudentService (CRUD)**
  - Detyrë: Krijimi i interface-it `StudentService`
  - Detyrë: Implementimi `StudentServiceImpl` me logjikën e biznesit
- **Historia 3.2: Ndërtimi i REST controller-ave**
  - Detyrë: Krijimi i `StudentController` me 6 endpoint-et
- **Historia 3.3: Shtimi i validimit dhe trajtimit të gabimeve**
  - Detyrë: Validimi me `@Valid` dhe Bean Validation
  - Detyrë: Krijimi i `GlobalExceptionHandler` me `@ControllerAdvice`

### Epik 4: Eksportimi në Excel
> Gjenerimi i një file-i `.xlsx` me listën e studentëve.

- **Historia 4.1: Integrimi i Apache POI**
  - Detyrë: Shtimi i varësisë `poi-ooxml`
- **Historia 4.2: Implementimi i ExcelExportService**
  - Detyrë: Krijimi i workbook-ut XSSF me titujt dhe stilin përkatës
  - Detyrë: Shtimi i endpoint-it `GET /api/students/export`

### Epik 5: Testimi dhe CI/CD
> Sigurimi i cilësisë dhe automatizimi i ndërtimit e deploy-imit.

- **Historia 5.1: Teste unitare me JUnit 5 + Mockito**
  - Detyrë: Testimi i të gjitha metodave të `StudentService`
- **Historia 5.2: Konfigurimi i pipeline-it Jenkins**
  - Detyrë: Krijimi i `Jenkinsfile` me 5 stage-t
- **Historia 5.3: Manifestet e deploy-imit OpenShift**
  - Detyrë: Krijimi i `deployment.yaml`, `service.yaml` dhe `route.yaml`

---

## 5. Planifikimi i Sprint-eve

### Sprint 1 — "Themelet e Projektit" (Java 1)
**Përmbajtja:** Epik 1 dhe Epik 2
**Qëllimi:** Të ngrihet struktura e projektit dhe shtresa e të dhënave.

| Historia | Pikët e Vlerësimit (Story Points) |
|----------|-----------------------------------|
| 1.1 Inicializimi Maven multi-modul | 3 |
| 1.2 Konfigurimi i varësive | 2 |
| 2.1 Entiteti Student | 3 |
| 2.2 DTO + Mapper | 3 |
| 2.3 Repository | 2 |

### Sprint 2 — "Funksionalitetet dhe Deploy-imi" (Java 2)
**Përmbajtja:** Epik 3, Epik 4 dhe Epik 5
**Qëllimi:** Të realizohet API-ja, eksportimi në Excel dhe pipeline-i CI/CD me deploy.

| Historia | Pikët e Vlerësimit (Story Points) |
|----------|-----------------------------------|
| 3.1 StudentService | 5 |
| 3.2 REST Controller | 3 |
| 3.3 Validimi dhe gabimet | 3 |
| 4.1 Integrimi i Apache POI | 2 |
| 4.2 ExcelExportService | 3 |
| 5.1 Teste unitare | 5 |
| 5.2 Pipeline Jenkins | 3 |
| 5.3 Manifestet OpenShift | 3 |

---

## 6. Backlog dhe Sprint Board

- **Backlog:** përmban të gjitha historitë e parealizuara, të renditura sipas prioritetit.
- **Sprint Board:** vizualizon punën e Sprint-it aktual në kolona:

| Kolona | Përshkrimi |
|--------|------------|
| Për t'u bërë (To Do) | Detyra të planifikuara por të panisura |
| Në proces (In Progress) | Detyra që po zhvillohen aktualisht |
| Në rishikim (In Review) | Detyra në pritje të rishikimit të kodit |
| E përfunduar (Done) | Detyra të mbaruara dhe të verifikuara |

---

## 7. Rrjedha e Punës (Workflow)

1. Zhvilluesi merr një detyrë nga kolona **Për t'u bërë** dhe e kalon në **Në proces**.
2. Pas përfundimit, detyra kalon në **Në rishikim**.
3. Pas miratimit, detyra shënohet si **E përfunduar**.
4. Në fund të çdo Sprint-i mbahet një **Sprint Review** dhe një **Retrospektivë**.
