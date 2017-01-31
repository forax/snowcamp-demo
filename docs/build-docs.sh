#!/usr/bin/env bash
asciidoctor notes.adoc -a os=tux -o notes-linux.html
asciidoctor notes.adoc -a os=win -o notes-windows.html
