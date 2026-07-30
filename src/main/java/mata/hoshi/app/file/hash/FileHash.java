package mata.hoshi.app.file.hash;

import mata.hoshi.app.PojaGenerated;

@PojaGenerated
public record FileHash(FileHashAlgorithm algorithm, String value) {}
