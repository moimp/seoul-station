package org.masil.seoulyeok.util;

import lombok.Value;

@Value(staticConstructor = "of")
public class Tuple<L, R> {

    L left;
    R right;
}
