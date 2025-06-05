# How to use this repo (Win 10)

## Step 0: Uninstall docker desktop (optional)
I have problems with Docker Desktop on windows, seems like I have to reinstall it every week just so it would work.
Only do this if Docker doesn't work.

1. Apps & Fetures > uninstall docker
2. Delete `C:\ProgramData\DockerDesktop`(ProgramData is a hidden folder)
3. Delete `C:\Program Files\Docker`
4. Delete `%APPDATA%\Docker`
5. Delete `%LOCALAPPDATA%\Docker`
6. Check `wsl --list --verbose` for docker related distros.
7. If you found one, delete it with `wsl --unregister <distribution_name>`

## Step 1: Install docker
[Docker site](https://www.docker.com)

## Step 2: Gradle
Use the command `docker pull gradle` to get the latest gradle image.

## Step 3: Docker compose
Run `docker compose up` in the main directory of the project, where the `docker-compose.yml` file is located.

Wait until its finished, the first time is going to be longer.

Watch out for the created distros names!
It will look like this:
```
...
[+] Running 5/5
 ✔ ubuntu                                              Built           0.0s 
 ✔ Network sqat-selenium-assignment_default            Created         0.1s 
 ✔ Container sqat-selenium-assignment-selenium-1       Created         0.6s 
 ✔ Container sqat-selenium-assignment-novnc-1          Created         0.1s 
 ✔ Container sqat-selenium-assignment-ubuntu-1         Created         0.1s
 ...
 ```

## Step 4: Run tests
Docker compose created three running containers, from which you will need the ubuntu one (not selenium, not novnc).

- Open a console for the running ubuntu distro: `docker exec -it <distro_name_ubuntu_1> bash`, in my case, its `sqat-selenium-assignment-ubuntu-1`.
- Depending on your docker compose file, you need to navigate to the folder that contains the tests, in this repository's case, to `/home/selenium/tests`.
- Open `http://localhost:8081/` to see the user interface of the ubuntu system, here you will see the tests being performed.
- Run tests with gradle: `gradle clean build test` (every run will be clean).