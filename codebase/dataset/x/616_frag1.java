        if (synthesis_type_name.equals("Sine Wave")) return SINE_WAVE; else if (synthesis_type_name.equals("Basic Tone")) return BASIC_TONE; else if (synthesis_type_name.equals("Stereo Panning")) return STEREO_PANNING; else if (synthesis_type_name.equals("Stereo Pingpong")) return STEREO_PINPONG; else if (synthesis_type_name.equals("FM Sweep")) return FM_SWEEP; else if (synthesis_type_name.equals("Decay Pulse")) return DECAY_PULSE; else if (synthesis_type_name.equals("White Noise")) return WHITE_NOISE; else throw new Exception("Unknown type of synthesis specified: " + synthesis_type_name + ".\n" + "Known types of synthesis are:\n" + "   Sine Wave, Basic Tone, Stereo Panning, Stereo Pingpong\n" + "   FM Sweep, White Noise and Decay Pulse.");