        compile("UnboundedStack.java", "// @(#)$Id: UnboundedStack.java 1199 2009-02-17 19:42:32Z smshaner $\n" + "\n" + "// Copyright (C) 1998, 1999 Iowa State University\n" + "\n" + "// This file is part of JML\n" + "\n" + "// JML is free software; you can redistribute it and/or modify\n" + "// it under the terms of the GNU General Public License as published by\n" + "// the Free Software Foundation; either version 2, or (at your option)\n" + "// any later version.\n" + "\n" + "// JML is distributed in the hope that it will be useful,\n" + "// but WITHOUT ANY WARRANTY; without even the implied warranty of\n" + "// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the\n" + "// GNU General Public License for more details.\n" + "\n" + "// You should have received a copy of the GNU General Public License\n" + "// along with JML; see the file COPYING.  If not, write to\n" + "// the Free Software Foundation, 675 Mass Ave, Cambridge, MA 02139, USA.\n" + "\n" + "\n" + "import java.util.*;\n" + "\n" + "public abstract class UnboundedStack {\n" + "\n" + "  /*@ public model Stack theStack;\n" + "    @ public initially theStack != null\n" + "    @                  && theStack.isEmpty();\n" + "    @*/\n" + "\n" + "  //@ public invariant theStack != null;\n" + "\n" + "  /*@ public normal_behavior\n" + "    @   requires !theStack.isEmpty();\n" + "    @   assignable theStack;\n" + "    @   ensures theStack.equals(\n" + "    @              \\old(theStack));\n" + "    @*/\n" + "  public abstract void pop( );\n" + "\n" + "  /*@ public normal_behavior\n" + "    @   assignable theStack;\n" + "    @   ensures theStack.equals(\n" + "    @              \\old(theStack.add(x)));\n" + "    @*/\n" + "  public abstract void push(Object x);\n" + "\n" + "  /*@ public normal_behavior\n" + "    @   requires !theStack.isEmpty();\n" + "    @   assignable \\nothing;\n" + "    @   ensures \\result == theStack.get(0);\n" + "    @*/\n" + "  public /*@ pure @*/ abstract Object top( );\n" + "}\n" + "// @(#)$Id: UnboundedStack2.java 1199 2009-02-17 19:42:32Z smshaner $\n" + "\n" + "// Copyright (C) 1998, 1999 Iowa State University\n" + "\n" + "// This file is part of JML\n" + "\n" + "// JML is free software; you can redistribute it and/or modify\n" + "// it under the terms of the GNU General Public License as published by\n" + "// the Free Software Foundation; either version 2, or (at your option)\n" + "// any later version.\n" + "\n" + "// JML is distributed in the hope that it will be useful,\n" + "// but WITHOUT ANY WARRANTY; without even the implied warranty of\n" + "// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the\n" + "// GNU General Public License for more details.\n" + "\n" + "// You should have received a copy of the GNU General Public License\n" + "// along with JML; see the file COPYING.  If not, write to\n" + "// the Free Software Foundation, 675 Mass Ave, Cambridge, MA 02139, USA.\n" + "\n" + "abstract class UnboundedStack2 {\n" + "\n" + "  /*@ public model non_null Stack theStack;\n" + "    @ public initially theStack.isEmpty();\n" + "    @*/\n" + "\n" + "  /*@ public normal_behavior\n" + "    @   requires !theStack.isEmpty();\n" + "    @   assignable theStack;\n" + "    @   ensures theStack.equals(\\old(theStack));\n" + "    @*/\n" + "  public abstract void pop( );\n" + "\n" + "  /*@ public normal_behavior\n" + "    @   assignable theStack;\n" + "    @   ensures theStack.equals(\\old(theStack.add(x)));\n" + "    @*/\n" + "  public abstract void push(Object x);\n" + "\n" + "  /*@ public normal_behavior\n" + "    @   requires !theStack.isEmpty();\n" + "    @   assignable \\nothing;\n" + "    @   ensures \\result == theStack.get(0);\n" + "    @*/\n" + "  public /*@ pure @*/ abstract Object top( );\n" + "}\n" + "// @(#)$Id: UnboundedStackAsArrayList.java 1199 2009-02-17 19:42:32Z smshaner $\n" + "\n" + "// Copyright (C) 1998, 1999 Iowa State University\n" + "\n" + "// This file is part of JML\n" + "\n" + "// JML is free software; you can redistribute it and/or modify\n" + "// it under the terms of the GNU General Public License as published by\n" + "// the Free Software Foundation; either version 2, or (at your option)\n" + "// any later version.\n" + "\n" + "// JML is distributed in the hope that it will be useful,\n" + "// but WITHOUT ANY WARRANTY; without even the implied warranty of\n" + "// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the\n" + "// GNU General Public License for more details.\n" + "\n" + "// You should have received a copy of the GNU General Public License\n" + "// along with JML; see the file COPYING.  If not, write to\n" + "// the Free Software Foundation, 675 Mass Ave, Cambridge, MA 02139, USA.\n" + "\n" + "\n" + "class UnboundedStackAsArrayList extends UnboundedStack {\n" + "\n" + "  protected ArrayList elems;\n" + "\n" + "  //@ protected invariant elems != null;\n" + "\n" + "  /*@ public normal_behavior\n" + "    @   assignable theStack;\n" + "    @   ensures theStack.isEmpty();\n" + "    @*/\n" + "  public UnboundedStackAsArrayList()\n" + "  {\n" + "    elems = new ArrayList();\n" + "  }\n" + "\n" + "  /*@ also\n" + "    @   protected normal_behavior\n" + "    @     requires !theStack.isEmpty();\n" + "    @     assignable theStack;\n" + "    @     ensures \\not_modified(elems);\n" + "    @*/\n" + "  public void pop( )\n" + "  {\n" + "    elems.remove(0);\n" + "  }\n" + "\n" + "  /*@ also\n" + "    @   protected normal_behavior\n" + "    @     assignable theStack;\n" + "    @     ensures \\not_modified(elems);\n" + "    @*/\n" + "  public void push(Object x)\n" + "  {\n" + "    elems.add(0,x);\n" + "  }\n" + "\n" + "  public Object top( )\n" + "  {\n" + "    return elems.get(0);\n" + "  }\n" + "\n" + "  public String toString() {\n" + "      StringBuffer ret = new StringBuffer(\"UnboundedStack[\");\n" + "      Iterator iter = elems.iterator();\n" + "      boolean first = true;\n" + "      while (iter.hasNext()) {\n" + "          Object o = iter.next();\n" + "          if (o != null) {\n" + "              ret.append(o.toString());\n" + "          } else {\n" + "              ret.append(\"null\");\n" + "          }\n" + "          if (first) {\n" + "              first = false;\n" + "          } else {\n" + "              ret.append(\", \");\n" + "          }\n" + "      }\n" + "      ret.append(\"]\");\n" + "      return ret.toString();\n" + "  }\n" + "}\n" + "// @(#)$Id: UnboundedStackRC.java 1199 2009-02-17 19:42:32Z smshaner $\n" + "\n" + "// Copyright (C) 1998, 1999 Iowa State University\n" + "\n" + "// This file is part of JML\n" + "\n" + "// JML is free software; you can redistribute it and/or modify\n" + "// it under the terms of the GNU General Public License as published by\n" + "// the Free Software Foundation; either version 2, or (at your option)\n" + "// any later version.\n" + "\n" + "// JML is distributed in the hope that it will be useful,\n" + "// but WITHOUT ANY WARRANTY; without even the implied warranty of\n" + "// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the\n" + "// GNU General Public License for more details.\n" + "\n" + "// You should have received a copy of the GNU General Public License\n" + "// along with JML; see the file COPYING.  If not, write to\n" + "// the Free Software Foundation, 675 Mass Ave, Cambridge, MA 02139, USA.\n" + "\n" + "\n" + "abstract class UnboundedStackRC {\n" + "\n" + "  /*@ public model non_null Stack theStack;\n" + "    @ public initially theStack.isEmpty();\n" + "    @*/\n" + "  //@ public invariant theStack != null;\n" + "\n" + "\n" + "  public abstract void pop( );\n" + "\n" + "  public abstract void push(Object x);\n" + "\n" + "  public abstract Object top( );\n" + "}\n" + "// @(#)$Id: UnboundedStackRC2.java 1199 2009-02-17 19:42:32Z smshaner $\n" + "\n" + "// Copyright (C) 1998, 1999 Iowa State University\n" + "\n" + "// This file is part of JML\n" + "\n" + "// JML is free software; you can redistribute it and/or modify\n" + "// it under the terms of the GNU General Public License as published by\n" + "// the Free Software Foundation; either version 2, or (at your option)\n" + "// any later version.\n" + "\n" + "// JML is distributed in the hope that it will be useful,\n" + "// but WITHOUT ANY WARRANTY; without even the implied warranty of\n" + "// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the\n" + "// GNU General Public License for more details.\n" + "\n" + "// You should have received a copy of the GNU General Public License\n" + "// along with JML; see the file COPYING.  If not, write to\n" + "// the Free Software Foundation, 675 Mass Ave, Cambridge, MA 02139, USA.\n" + "\n" + "\n" + "abstract class UnboundedStackRC2 {\n" + "\n" + "  /*@ public model Stack theStack;\n" + "    @ public initially theStack != null && theStack.isEmpty();\n" + "    @*/\n" + "  //@ public invariant theStack != null;\n" + "\n" + "  public abstract void pop( );\n" + "\n" + "  public abstract void push(Object x);\n" + "\n" + "  public abstract Object top( );\n" + "}\n", "");