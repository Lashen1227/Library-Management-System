# Library Management System

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![JavaFX](https://img.shields.io/badge/JavaFX-007396?style=for-the-badge&logo=java&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![CSS](https://img.shields.io/badge/CSS-1572B6?style=for-the-badge&logo=css3&logoColor=white)

This is a Desktop Application created using Java, JavaFX, MySQL, and styled with CSS. The Library Management System allows to manage library operations efficiently.

## Table of Contents
- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

## Features
- User Authentication
- Book Management
- Member Management
- Borrowing and Returning Books
- Author and Publisher Management

## Installation

### Prerequisites
- Java JDK 8 or higher
- JavaFX
- MySQL
- Maven

### Steps
1. Clone the repository:
    ```sh
    git clone https://github.com/Lashen1227/Library-Management-System.git
    cd Library-Management-System
    ```

2. Set up the MySQL database:
    - Create a database named `library_db`.
    - Execute the SQL scripts in the `sql` directory to set up the necessary tables.

3. Update the database configuration:
    - Edit the `DBConnection.java` file located in `src/main/java/org/example/util/`.
    - Update the database URL, username, and password.

4. Build the project:
    ```sh
    mvn clean install
    ```

5. Run the application:
    ```sh
    mvn exec:java -Dexec.mainClass="org.example.Main"
    ```

## Usage
1. Launch the application.
2. Log in using your credentials.
3. Use the dashboard to manage books, members, borrowing and returning books, authors, and publishers.

## Contributing
Contributions are what make the open-source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. Fork the project.
2. Create your feature branch (`git checkout -b feature/AmazingFeature`).
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`).
4. Push to the branch (`git push origin feature/AmazingFeature`).
5. Open a pull request.

## License
Distributed under the MIT License. See `LICENSE` for more information.

## Contact
Lashen Martino - [Email](mailto:lashenmartino@gmail.com)

Project Link: [https://github.com/Lashen1227/Library-Management-System](https://github.com/Lashen1227/Library-Management-System.git)
