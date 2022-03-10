set positional-arguments

bazelisk_path := `which bazelisk`
workspace := `pwd`

# Print available commands.
default:
  @just --list

# Pin maven dependencies.
pin:
  bazelisk run @unpinned_maven//:pin

# Analyzes the given targets and queries the action graph.
build *args:
  bazelisk build "$@"

# Removes output files and optionally stops the server.
clean *args:
  bazelisk clean "$@"

# Builds and runs the specified test targets.
test *args:
  bazelisk test "$@"
