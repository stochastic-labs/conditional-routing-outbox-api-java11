package com.stochasticlabs.conditionalroutingoutboxapijava11.factory;

import com.stochasticlabs.conditionalroutingoutboxapijava11.dto.InputDTO;
import com.stochasticlabs.conditionalroutingoutboxapijava11.entity.Input;

public class InputFactory {
    public static Input create(InputDTO inputDTO) {
        return new Input(inputDTO.getInteger());
    }
}
