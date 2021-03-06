    private void hookContextMenu(TabControl tabControl, final PageController pageController) {

        if (tabControl != null) {

            MenuManager menuMgr = new MenuManager("#PopupMenu");

            menuMgr.setRemoveAllWhenShown(true);

            menuMgr.addMenuListener(new IMenuListener() {



                public void menuAboutToShow(IMenuManager manager) {

                    LogControlView.this.fillContextMenu(manager, pageController);

                }

            });

            if (tabControl.getClass() == TreeControl.class) {

                StructuredViewer viewer = ((TreeControl) tabControl).getViewer();

                Menu menu = menuMgr.createContextMenu(viewer.getControl());

                viewer.getControl().setMenu(menu);

                getSite().registerContextMenu(menuMgr, viewer);

            }

            if (tabControl.getClass() == TableControl.class) {

                Control control = ((TableControl) tabControl).getContextMenuControl();

                Menu menu = menuMgr.createContextMenu(control);

                control.setMenu(menu);

            }

        }

    }
