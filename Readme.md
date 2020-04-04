## Introduction

This is a Maven plugin aiming to search and replace other variables or normal text using regular expressions and so create a new variable.

### Example

Creating a new variable:
```xml
<build>
    <plugins>
        <plugin>
            <groupId>io.github.1tchy</groupId>
            <artifactId>variable-search-replace-plugin</artifactId>
            <version>1.0-SNAPSHOT</version>
            <executions>
                <execution>
                    <goals>
                        <goal>replace</goal>
                    </goals>
                    <configuration>
                        <text>${project.version}</text>
                        <search>-SNAPSHOT</search>
                        <replacement>.0.0.0</replacement>
                        <variableName>project.version.nosnapshot</variableName>
                    </configuration>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```
Using the new variable
```xml
<executions>
    <execution>
        <id>create-app</id>
        <phase>package</phase>
        <goals>
            <goal>exec</goal>
        </goals>
        <configuration>
            <executable>${java.home}/bin/jpackage</executable>
            <arguments>
                <argument>--module-path</argument>
                <argument>${copied-dependencies}</argument>
                <argument>--module</argument>
                <argument>${app.main.module}/${app.main.class}</argument>
                <argument>--name</argument>
                <argument>My super App</argument>
                <argument>--app-version</argument>
                <argument>${project.version.nosnapshot}</argument> <!-- variable usage -->
            </arguments>
        </configuration>
    </execution>
</executions>
```

## Basic Implementation

The core implementation of this plugin is basically no more than:
```
setProperty(variableName, text.replaceAll(search, replacement));
```

## License

> This is free and unencumbered software released into the public domain.
>
> Anyone is free to copy, modify, publish, use, compile, sell, or distribute this software, either in source code form or as a compiled binary, for any purpose, commercial or non-commercial, and by any means.
>
> In jurisdictions that recognize copyright laws, the author or authors of this software dedicate any and all copyright interest in the software to the public domain. We make this dedication for the benefit of the public at large and to the detriment of our heirs and successors. We intend this dedication to be an overt act of relinquishment in perpetuity of all present and future rights to this software under copyright law.
>
> THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
>
> For more information, please refer to <http://unlicense.org/>
