
   function ALPHABET()
            {Skip=""
             if (arguments.length>0)
                Skip=arguments[0]
             Inactive=""
             if (arguments.length>1)
                Inactive=arguments[1]
             Alphabet  ="ABCDEFGHIJKLMNOPQRSTUVWXYZ"
             Alpha_Line=""
             for (i=0;i<26;i++)
                 {Letter=Alphabet.substr(i,1)
                  if ((Alpha_Line!="" )&&  // Suppress ending "|" on the sides
                      (!((Skip   =="Z")&&
                         (Letter =="Z"))))
                     Alpha_Line=Alpha_Line+"<span style='color: green'>|</span>"
                  if (Letter!=Skip)  // Suppress "Skip" letter (if any)
                     if (Inactive.indexOf(Letter)==-1)  // Letter is active
                        Alpha_Line=Alpha_Line+" <A HREF='/category?startBy="+Letter+"'>"+Letter+"</A>"
                     else  // Letter is inactive
                        Alpha_Line=Alpha_Line+" <span style='color: gray'>"+Letter+"</span>"
                  if ((Letter!="Z")&&  // Suppress extra blanks
                      (Letter!=Skip))
                     Alpha_Line=Alpha_Line+" "
                 }
             return Alpha_Line
            }
//-->
