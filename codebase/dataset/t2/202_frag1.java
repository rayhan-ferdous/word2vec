                        break;

                    case BLUE:

                        {

                            alt1 = 2;

                        }

                        break;

                    case RED:

                        {

                            alt1 = 3;

                        }

                        break;

                    default:

                        if (state.backtracking > 0) {

                            state.failed = true;

                            return element;

                        }

                        NoViableAltException nvae = new NoViableAltException("", 1, 0, input);

                        throw nvae;

                }

                switch(alt1) {

                    case 1:

                        {

                            if (state.backtracking == 0) {

                            }

                            {

                                a10 = (Token) match(input, BLOND, FOLLOW_BLOND_in_parse_org_emftext_language_models_Model258);

                                if (state.failed) return element;

                                if (state.backtracking == 0) {

                                    if (terminateParsing) {

                                        throw new org.emftext.language.models.resource.model.mopp.ModelTerminateParsingException();

                                    }

                                    if (element == null) {

                                        element = org.emftext.language.models.ModelsFactory.eINSTANCE.createModel();

                                    }

                                    if (a10 != null) {

                                        org.emftext.language.models.resource.model.IModelTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("BLOND");

                                        tokenResolver.setOptions(getOptions());

                                        org.emftext.language.models.resource.model.IModelTokenResolveResult result = getFreshTokenResolveResult();

                                        tokenResolver.resolve(a10.getText(), element.eClass().getEStructuralFeature(org.emftext.language.models.ModelsPackage.MODEL__HAIR), result);

                                        java.lang.Object resolvedObject = result.getResolvedToken();

                                        if (resolvedObject == null) {

                                            addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime.CommonToken) a10).getLine(), ((org.antlr.runtime.CommonToken) a10).getCharPositionInLine(), ((org.antlr.runtime.CommonToken) a10).getStartIndex(), ((org.antlr.runtime.CommonToken) a10).getStopIndex());

                                        }

                                        org.emftext.language.models.Color resolved = (org.emftext.language.models.Color) resolvedObject;

                                        if (resolved != null) {

                                            element.eSet(element.eClass().getEStructuralFeature(org.emftext.language.models.ModelsPackage.MODEL__HAIR), resolved);

                                        }

                                        collectHiddenTokens(element);

                                        copyLocalizationInfos((CommonToken) a10, element);

                                    }

                                }

                            }

                        }

                        break;

                    case 2:

                        {

                            if (state.backtracking == 0) {

                            }

                            {

                                a11 = (Token) match(input, BLUE, FOLLOW_BLUE_in_parse_org_emftext_language_models_Model287);

                                if (state.failed) return element;

                                if (state.backtracking == 0) {

                                    if (terminateParsing) {

                                        throw new org.emftext.language.models.resource.model.mopp.ModelTerminateParsingException();

                                    }

                                    if (element == null) {

                                        element = org.emftext.language.models.ModelsFactory.eINSTANCE.createModel();

                                    }

                                    if (a11 != null) {

                                        org.emftext.language.models.resource.model.IModelTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("BLUE");

                                        tokenResolver.setOptions(getOptions());

                                        org.emftext.language.models.resource.model.IModelTokenResolveResult result = getFreshTokenResolveResult();

                                        tokenResolver.resolve(a11.getText(), element.eClass().getEStructuralFeature(org.emftext.language.models.ModelsPackage.MODEL__HAIR), result);

                                        java.lang.Object resolvedObject = result.getResolvedToken();

                                        if (resolvedObject == null) {

                                            addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime.CommonToken) a11).getLine(), ((org.antlr.runtime.CommonToken) a11).getCharPositionInLine(), ((org.antlr.runtime.CommonToken) a11).getStartIndex(), ((org.antlr.runtime.CommonToken) a11).getStopIndex());

                                        }

                                        org.emftext.language.models.Color resolved = (org.emftext.language.models.Color) resolvedObject;

                                        if (resolved != null) {

                                            element.eSet(element.eClass().getEStructuralFeature(org.emftext.language.models.ModelsPackage.MODEL__HAIR), resolved);

                                        }

                                        collectHiddenTokens(element);

                                        copyLocalizationInfos((CommonToken) a11, element);

                                    }

                                }

                            }

                        }

                        break;

                    case 3:

                        {

                            if (state.backtracking == 0) {

                            }

                            {

                                a12 = (Token) match(input, RED, FOLLOW_RED_in_parse_org_emftext_language_models_Model316);

                                if (state.failed) return element;

                                if (state.backtracking == 0) {

                                    if (terminateParsing) {

                                        throw new org.emftext.language.models.resource.model.mopp.ModelTerminateParsingException();

                                    }

                                    if (element == null) {

                                        element = org.emftext.language.models.ModelsFactory.eINSTANCE.createModel();

                                    }

                                    if (a12 != null) {

                                        org.emftext.language.models.resource.model.IModelTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("RED");

                                        tokenResolver.setOptions(getOptions());

                                        org.emftext.language.models.resource.model.IModelTokenResolveResult result = getFreshTokenResolveResult();

                                        tokenResolver.resolve(a12.getText(), element.eClass().getEStructuralFeature(org.emftext.language.models.ModelsPackage.MODEL__HAIR), result);

                                        java.lang.Object resolvedObject = result.getResolvedToken();

                                        if (resolvedObject == null) {

                                            addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime.CommonToken) a12).getLine(), ((org.antlr.runtime.CommonToken) a12).getCharPositionInLine(), ((org.antlr.runtime.CommonToken) a12).getStartIndex(), ((org.antlr.runtime.CommonToken) a12).getStopIndex());

                                        }

                                        org.emftext.language.models.Color resolved = (org.emftext.language.models.Color) resolvedObject;

                                        if (resolved != null) {

                                            element.eSet(element.eClass().getEStructuralFeature(org.emftext.language.models.ModelsPackage.MODEL__HAIR), resolved);

                                        }

                                        collectHiddenTokens(element);

                                        copyLocalizationInfos((CommonToken) a12, element);

                                    }

                                }

                            }

                        }

                        break;

                }

                if (state.backtracking == 0) {

                }

                a13 = (Token) match(input, 19, FOLLOW_19_in_parse_org_emftext_language_models_Model338);

                if (state.failed) return element;

                if (state.backtracking == 0) {

                    if (element == null) {

                        element = org.emftext.language.models.ModelsFactory.eINSTANCE.createModel();

                    }

                    collectHiddenTokens(element);

                    copyLocalizationInfos((CommonToken) a13, element);

                }

                if (state.backtracking == 0) {

                }

                a14 = (Token) match(input, 20, FOLLOW_20_in_parse_org_emftext_language_models_Model349);

                if (state.failed) return element;

                if (state.backtracking == 0) {

                    if (element == null) {

                        element = org.emftext.language.models.ModelsFactory.eINSTANCE.createModel();

                    }

                    collectHiddenTokens(element);
