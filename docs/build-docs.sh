#!/usr/bin/env bash
docs_home=$(dirname $0)
asciidoctor $docs_home/images.adoc -r asciidoctor-diagram -a os=tux -o - > /dev/null
asciidoctor $docs_home/notes.adoc -r asciidoctor-diagram -a os=tux -o $docs_home/notes-linux.html
asciidoctor $docs_home/notes.adoc -r asciidoctor-diagram -a os=win -o $docs_home/notes-windows.html
