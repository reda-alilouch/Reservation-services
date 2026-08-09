# Architecture du service hôtel

Chaque fonctionnalité est autonome sous `feature/<feature>` :

```text
feature/hotel/
  domain/                 # règles et modèles métier purs
  application/
    port/in/              # cas d'utilisation
    port/out/             # abstractions demandées à l'extérieur
    service/              # orchestration sans Spring/JPA
  adapter/in/web/         # REST, validation, DTO HTTP
  adapter/out/persistence/# JPA et implémentation des ports
feature/room/             # extension réservée aux cas d'utilisation chambres
shared/                   # erreurs métier et assemblage Spring transversal
```

La dépendance va toujours vers le cœur : `adapter -> application -> domain`.
Les détails techniques (HTTP, JWT et JPA) ne sont pas utilisés par le domaine ni par les ports de cas d'utilisation.

Pour ajouter un cas d'utilisation, créez d'abord son port entrant dans `application/port/in`, puis son service, et enfin ses adaptateurs. Ne faites pas dépendre le service d'application de classes Spring, de DTO HTTP ou de repositories JPA.