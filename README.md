# lightweight-github-client

Lightweight github-client for tracking your favorite repositories. Issues of your favorite repositories on a single page.

## Usage

Clone the repository `git clone https://github.com/theshortman/lightweight-github-client && cd lightweight-github-client`.

### Config

Edit `./src/main/kotlin/Config.kt`.

Add your Github access token and favorite repositories to the file.

### Build and run

Build `./gradlew build`.

This generates an optimized build of the project in `./build/distributions`.

Go to `./build/distributions` and open index.html in a browser or start local server from the folder.

### Run for development

`./gradlew browserDevelopmentRun --continuous`

It starts server on [http://localhost:8080](http://localhost:8080).

## Contribute

All contributions are welcome.

[CONTRIBUTING.md](https://github.com/theshortman/lightweight-github-client/CONTRIBUTING.md)

