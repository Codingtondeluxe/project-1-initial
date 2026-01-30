# Reflection Log

This document captures reflections on the development of 3D geometric classes in Java, focusing on design patterns, principles, and lessons learned.

I looked more into the principle of encapsulation and how this code follows it. Encapsulation is important because it prevents other code from putting your objects into invalid or surprising states. Access to internal data is controlled through well-defined public methods rather than direct access to the field. This lets you make changes to parts of the program without causing problems elsewhere. In this code, Immutable Value Objects were used frequently to guarantee the object never changes. 