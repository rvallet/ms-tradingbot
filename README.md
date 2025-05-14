# Microservice Trading-Bot pour l'API Binance

Ce microservice est conçu pour interagir avec l'API de Binance afin de faciliter le trading automatisé. Il permet aux utilisateurs de gérer leurs transactions, d'obtenir des données de marché et d'exécuter des ordres de manière automatisée.

## Table des matières

- [Fonctionnalités](#fonctionnalités)
- [Technologies utilisées](#technologies-utilisées)
- [Prérequis](#prérequis)
- [Installation](#installation)
- [Utilisation](#utilisation)
- [License](#license)

## Fonctionnalités

- Connexion à l'API de Binance pour récupérer les données de marché.
- Exécution d'ordres d'achat et de vente.
- Gestion des transactions.
- Récupération des historiques de transactions.
- Notifications sur les performances du trading.

## Technologies utilisées

- Java 21
- Spring Boot 3.4.4
- Maven 3.14.0
- API de Binance

## Prérequis

- Java JRE ou JDK 21 installé sur votre machine.
- MongoDB 6.0 ou supérieur pour la gestion de la base de données.

## Installation

1. Clonez le dépôt sur votre machine locale :
   ```bash
   git clone https://github.com/rvallet/ms-tradingbot.git
   ````
2. Accédez au répertoire du projet :
   ```bash
   cd ms_tradingbot
   ```
3. Générez le projet :
   ```bash
   mvn clean package
   ```
4. Lancez l'application :
   ```bash
   java -jar $(ls -t target/*.jar | head -n 1)
   ```

## Utilisation

Lorsque l'application est lancée, exécuter les requêtes GET, POST, PUT et DELETE sur les endpoints suivants :
- http://localhost:9090/swagger-ui/index.html

## License

Ce projet est sous Licence MIT. Consultez le fichier [LICENSE](LICENSE) pour plus de détails.
