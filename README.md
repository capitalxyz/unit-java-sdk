# unit_sdk

Based off of https://github.com/unit-finance/unit-node-sdk.

## Installation 

### **Brew**

Install [Homebrew](https://brew.sh/) to manage macOS dependencies.

```sh
/bin/sh -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
```

Important: Make sure you follow the "Next steps" instructions after you run the above install command, otherwise `brew` will not be available on your `PATH`.

### Install Brew dependencies 

```sh
brew bundle install
```

## Usage

Build library:

```
just build //unit_sdk
```

Test everything: 

```
just test //... --test_output=all
```

Update pinned dependencies: 

```
just pin
```
