
 # -- check the path
 $pwd
 /home/<username>

 # --- clone the  repository
 git clone https://github.com/rnhc1000/calculatorWeb.git

 # --- move to the project directory
 cd calculatorWeb

 # --- check if Dockerfile and dockerBuild.sh are there
 ls -l


# - dockerBuild.sh
# - Dockerfile

 # -- pull java 21 image
 docker pull amazoncorretto:21-alpine3.20-jdk

# --- check if build is ok
./gradlew build && java -jar build/libs/calculatorWeb-0.0.1-SNAPSHOT.jar

# --- preparing the directory layout
mkdir -p build/dependency

cd build/dependency
jar -xf ../libs/*.jar

# --- mode to where Dockerfile is
cd ../../

#--- build the image, adjusting the version
docker build --build-arg DEPENDENCY=build/dependency -t rnhc757/images:calculatorWeb:x-y-z .

# --- check the image
docker image | grep Web



