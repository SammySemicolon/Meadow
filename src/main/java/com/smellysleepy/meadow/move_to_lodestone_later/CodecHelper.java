package com.smellysleepy.meadow.move_to_lodestone_later;

import com.mojang.datafixers.util.*;
import com.mojang.serialization.*;

import java.util.*;

public class CodecHelper {

    //TODO: this is awesome move it to lodestone
    public static <O> Codec<Optional<O>> optionalCodec(Codec<O> codec) {
        return Codec.either(
                codec,
                Codec.unit(Unit.INSTANCE)
        ).xmap(
                e -> e.map(Optional::of, u -> Optional.empty()),
                o -> o.map(Either::<O, Unit>left).orElse(Either.right(Unit.INSTANCE))
        );
    }
}
