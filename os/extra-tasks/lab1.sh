#!/bin/bash
grep -Eo '([^#]*#!.*)|(^[^#]*)' $1
