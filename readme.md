I have problems with Docker Desktop on windows, seems like I have to reinstall it every week just so it would work.

# How to use this repo (Win 10)

## Step 0: Uninstall docker desktop (optional)
Only do this if it doesn't run.

1. Apps & Fetures > uninstall docker
2. Delete `C:\ProgramData\DockerDesktop`(ProgramData is a hidden folder)
3. Delete `C:\Program Files\Docker`
4. Delete `%APPDATA%\Docker`
5. Delete `%LOCALAPPDATA%\Docker`
6. Check `wsl --list --verbose` for docker related distros.
7. If you found one, delete it with `wsl --unregister <distribution_name>`

## Step 1: Install docker
[Docker](https://www.docker.com)

## Step 2: Gradle
Use the command `docker pull gradle` to get the latest gradle image.

## Step 3: Run build.gradle
Instructions from [this](https://github.com/JacksonFurrier/SQAT) repo.

- On Nix systems `docker run -i -t --rm -u gradle -v "$PWD":YOUR_PATH_TO_SAMPLE -w YOUR_PATH_TO_SAMPLE gradle gradle <gradle-task>`
- On Windows systems `docker run -i -t --rm -u gradle -v YOUR_PATH_TO_SAMPLE:YOUR_PATH_TO_SAMPLE -w YOUR_PATH_TO_SAMPLE gradle gradle <gradle-task>`

### Example on windows:
- project folder `C:\dev\project`
- Tasks: `build` and `test`

Te command:
`docker run -i -t --rm -u gradle -v "C:\dev\project:C:\dev\project" -w "C:\dev\project" gradle gradle build test`