# 🎓 CampusDB — Lab 15 : SQLite et Android – Gestion des Étudiants

## Objectif

Construire une application Android de gestion des étudiants basée sur une base de données **SQLite embarquée**. L'application couvre une couche métier (modèle), une couche d'accès aux données (SQLiteOpenHelper + service CRUD) et une interface graphique (Ajouter, Rechercher, Supprimer).

---

## Concepts Abordés

- Création d'une base SQLite locale avec `SQLiteOpenHelper`
- Opérations CRUD avec `ContentValues` et `Cursor`
- Pattern Service pour l'accès aux données
- `rawQuery` et `query` pour la lecture
- Gestion des cas d'erreur (ID vide, étudiant introuvable)
- Interface utilisateur avec `EditText`, `Button`, `TextView`

---

## Aperçu de l'Application

**CampusDB** est une application de gestion d'étudiants avec deux sections :

| Section              | Description                                      |
|---------------------|--------------------------------------------------|
| Ajouter un étudiant | Saisir nom et prénom → bouton Ajouter            |
| Rechercher/Supprimer| Saisir un ID → Rechercher ou Supprimer           |

---
## DEMO 


https://github.com/user-attachments/assets/d3f314e5-9993-4f7f-82e7-62f16e61b19e



## Structure du Projet

```
CampusDB/
├── java/com/example/campusdb/
│   ├── MainActivity.java
│   ├── model/
│   │   └── Student.java
│   ├── util/
│   │   └── CampusDbHelper.java
│   └── service/
│       └── StudentService.java
├── res/
│   ├── layout/
│   │   └── activity_main.xml
│   └── values/
│       ├── colors.xml
│       ├── strings.xml
│       └── themes.xml
└── AndroidManifest.xml
```

---

## Architecture

```
MainActivity (UI)
      │
      ▼
StudentService (CRUD)
      │
      ▼
CampusDbHelper (SQLiteOpenHelper)
      │
      ▼
Base SQLite locale : campus.db
      └── table : student (id, last_name, first_name)
```

---

## Détails Clés de l'Implémentation

### Création de la table
```java
private static final String CREATE_TABLE_STUDENT =
        "create table student(" +
                "id INTEGER primary key autoincrement," +
                "last_name TEXT," +
                "first_name TEXT)";
```

### Insertion
```java
ContentValues values = new ContentValues();
values.put("last_name", s.getLastName());
values.put("first_name", s.getFirstName());
db.insert("student", null, values);
```

### Recherche par ID
```java
Cursor c = db.query("student", COLUMNS,
        "id = ?", new String[]{String.valueOf(id)},
        null, null, null, null);
if (c.moveToFirst()) {
    student.setId(c.getInt(0));
    student.setLastName(c.getString(1));
    student.setFirstName(c.getString(2));
}
```

### Suppression
```java
db.delete("student", "id = ?",
        new String[]{String.valueOf(s.getId())});
```

---

## Choix de Design

- **Thème :** Vert sarcelle / Fond menthe
- **Palette de couleurs :** Teal (`#00695C`), Teal clair (`#4DB6AC`), Fond (`#E0F2F1`), Rouge suppression (`#C62828`)
- **Deux cartes** séparant l'ajout de la recherche/suppression

---

## Comment Exécuter

1. Cloner ou ouvrir le projet dans **Android Studio**
2. Vérifier que le Min SDK est défini à **24**
3. Lancer sur un émulateur ou appareil physique (Android 7.0+)
4. Tester les opérations :
   - Saisir nom et prénom → **Ajouter**
   - Saisir l'ID retourné → **Rechercher** pour vérifier
   - Saisir l'ID → **Supprimer** pour effacer l'étudiant
5. Vérifier les logs dans **Logcat** (filtre `CampusDB`)

---

## Référence du Lab

- **Numéro du lab :** 15
- **Titre :** SQLite et Android – Gestion Simple des Étudiants
- **Langage :** Java
- **Min SDK :** 24 (Android 7.0 Nougat)
- **Base de données :** SQLite embarquée (`campus.db`)
