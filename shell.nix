with import <nixpkgs> {}; {
  env = stdenv.mkDerivation {
    name = "env";
    buildInputs = with pkgs; [
      sbt
    ];
  };
}
