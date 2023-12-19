## Para hacerlo funcionar en mac M1

1. cd /Users/<username>/.m2/repository/net/sourceforge/tess4j/tess4j/5.8.0/
2. mkdir darwin
3. jar uf tess4j-5.8.8.jar darwin/
4. brew info tesseract (here you can find path to libtesseract.4.dylib)
5. cp /opt/homebrew/Cellar/tesseract/5.3.3/lib/libtesseract.5.dylib darwin/libtesseract.dylib
6. jar uf tess4j-5.8.8.jar darwin/libtesseract.dylib
7. jar tf tess4j-5.8.8.jar