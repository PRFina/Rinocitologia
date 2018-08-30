# Rinocitologia

## Whats this?
JavaFx + CLIPS Engine + Python software - Graduation Thesis @Uniba. 
System to support medic specialist in _rhinitis and cytology_.

<img src="http://whoami.altervista.org/Materiale/Preview.png" alt="Cells revisioned by specialist">

## Features
<dl>
  <dt>Python</dt>
  <ul>
  <li>cells recognition</li>
  <li>cells classification through neural network</li>
  <li>Extraction of informations from "Tessera Sanitaria" (card used in Italy containing basic anagraphic informations about the patient)</li>
  </ul>
  
  <dt>Java - JavaFx</dt>
  <ul>
  <li>GUI cross platform</li>
  <li>convalidation system for classificated cells</li>
  <li>handling of pollens calendar for south italy</li>
  <li>patient management
  <ul>
  <li>patient's history</li>
  <li>suggested therapies</li>
  <li>reports in pdf and json</li>
  <p>Reports in json are useful to pass informations from system to system. In the future it could be implemented an online version of this system and json files are easy to use to save and share informations about the patient.</p>
  </ul>
  </li>
  </ul>
  
 <dt>CLIPS Engine</dt>
 <ul>
  <li>diagnostic engine</li>
  <p>Uses informations in patient's history and cytology informations obtained from the classification subsytem to retrieve a diagnosis for the patient. Diagnosis should be approved by the medic (or revisisted) before becoming the effective one.</p>
  </ul>
</dl>


## Dependencies
<dl>
<dt>Java</dt>
  <ul>
    <li>
      <a href="https://www.py4j.org">Py4j</a> - interaction between java and python.
      Let python access JVM objects.
    </li>
    <li>
      <a href="https://www.oracle.com/technetwork/java/javase/overview/javafx-overview-2158620.html">JavaFx</a> - GUI Crossplatform
      <ul>
        <li><a href="https://github.com/jfoenixadmin/JFoenix">JFoenix</a> - A JavaFx material design library</li>
      </ul>
    </li>
    <li>
     <a href="https://github.com/google/gson">Gson</a> - A Java serialization/deserialization library to convert Java Objects into JSON and back
    </li>
    <li>
      <a href="https://itextpdf.com">iText</a> - Easy PDF generation and manipulation for Java and .NET developers
    </li>
    <li>
      <a href="www.jetbrains.com/help/idea/annotating-source-code.html">Annotations</a> - Refer to this link to add annotations.jar in IntelliJ Project
    </li>
    <li>
      <a href="http://sourceforge.net/projects/clipsrules/files/CLIPS/6.30/">ClipsJNI</a> - Clips Java Native Interface
      <ul>
        <li>
          <a href="https://bitbucket.org/fverdoja/xclipsjni/wiki/Home">Italian page describing how to use it</a>
        </li>
     </ul>
   </li>
  </ul>
 </dl>
 <dl>
 <dt>Python</dt>
  <ul>
    <li>
      <a href="https://www.py4j.org">Py4j</a> - interaction between java and python.
      Let python access JVM objects.
    </li>
    <li>
      <a href="https://opencv.org">OpenCV</a> - Open Source Computer Vision Library, Python port
      <ul><li>Requires <b>numpy</b></li></ul>
    </li>
    <li>
      <a href="https://anaconda.org/lightsource2-tag/pyzbar">pyzbar</a>
      <ul>
        <li>Requires <a href="http://zbar.sourceforge.net">ZBar</a></li>
      </ul>
     </li>
     <li>
        <a href="https://keras.io/">Keras</a>
     </li>
     <li>
        <a href="https://www.tensorflow.org/">Tensorflow</a>
     </li>
     </ul>
     </dl>
     <dl>
      <dt>Anaconda distro</dt>
      <ul>
          <li>
             <a href="https://matplotlib.org">Matplotlib</a>
          </li>
               <li>
                  <a href="https://www.scipy.org/">Scipy</a>
               </li>
                    <li>
                       <a href="https://scikit-image.org">Scikit Image</a>
                    </li>
  </ul>
</dl>

