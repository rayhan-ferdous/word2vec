                        errorMessage = iare.getMessage();

                        return null;

                    } catch (IllegalAccessException iae) {

                        browser = null;

                        errorMessage = iae.getMessage();

                        return browser;

                    } catch (InvocationTargetException ite) {

                        browser = null;

                        errorMessage = ite.getTargetException().getClass() + ": " + ite.getTargetException().getMessage();

                        return browser;

                    }

                }

                browser = null;

                break;

            case MRJ_3_0:

            case MRJ_3_1:

                browser = "";

                break;

            case WINDOWS_NT:

                browser = "cmd.exe";

                break;

            case WINDOWS_9x:

                browser = "command.com";

                break;

            case OTHER:

            default:

                browser = "netscape";

                break;

        }

        return browser;

    }
