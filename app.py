from flask import Flask, request, jsonify, send_from_directory
from langchain_ollama import OllamaLLM
from langchain_core.prompts import ChatPromptTemplate
import edge_tts
import os
import asyncio
import hashlib
from langdetect import detect  # Language detection library
import threading
import requests

app = Flask(__name__)

# Define a directory to save the audio files
AUDIO_DIR = "audio_files"
if not os.path.exists(AUDIO_DIR):
    os.makedirs(AUDIO_DIR)

# Initialize the model
model = OllamaLLM(model="llama3.1")


# Hash function to cache audio files based on response text
def hash_response(response_text):
    return hashlib.md5(response_text.encode()).hexdigest()
# Map languages to Edge TTS voices
VOICE_MAP = {
    'ar': 'ar-EG-SalmaNeural',  # Arabic
    'en': 'en-US-JennyNeural',  # English
    'es': 'es-ES-ElviraNeural',  # Spanish
    'fr': 'fr-FR-DeniseNeural',  # French
    'de': 'de-DE-KatjaNeural',  # German
    # Add other languages as needed
}


def download_audio_file(audio_url, save_path):
    try:
        # Send a GET request to the audio URL
        response = requests.get(audio_url, stream=True)

        # Check if the request was successful
        if response.status_code == 200:
            # Open the file in write-binary mode and write the contents of the response
            with open(save_path, 'wb') as audio_file:
                for chunk in response.iter_content(chunk_size=1024):
                    if chunk:  # Filter out keep-alive chunks
                        audio_file.write(chunk)
            print(f"Audio file downloaded successfully: {save_path}")
        else:
            print(f"Failed to download audio. HTTP Status Code: {response.status_code}")
    except Exception as e:
        print(f"Error occurred: {e}")


# Function to detect language and get the corresponding voice
def detect_language(text):
    try:
        lang_code = detect(text)  # Detect the language of the input text
        print(f"Detected language: {lang_code}")
        return lang_code, VOICE_MAP.get(lang_code, 'en-US-JennyNeural')  # Default to English if language not in map
    except Exception as e:
        print(f"Error detecting language: {e}")
        return 'en', 'en-US-JennyNeural'  # Default to English in case of error


def generate_template(lang_code):
    if lang_code == 'ar':
        # Arabic template
        return """
        أنت فوزية، مساعدة المتحف المصري الكبير.المتحف المصري الكبير هو متحف قادم يقع بالقرب من أهرامات الجيزة في القاهرة، مصر. بمجرد اكتماله، سيكون أكبر متحف أثري في العالم، مخصص لحضارة واحدة. فيما يلي بعض التفاصيل الرئيسية حول المتحف:

نظرة عامة:
الموقع: على بعد حوالي 2 كم من هضبة الجيزة، حيث تقع الأهرامات العظيمة الشهيرة وأبو الهول.
الحجم: يمتد المتحف على مساحة 480.000 متر مربع، ويضم مجموعة ضخمة من القطع الأثرية وقاعات العرض ومراكز الحفظ والأماكن العامة.
الافتتاح: على الرغم من تأخيره عدة مرات، من المتوقع أن يفتح المتحف أبوابه بالكامل في المستقبل القريب، مع اكتمال بعض الأقسام بالفعل.
المجموعات:
مجموعة توت عنخ آمون: سيضم المتحف المصري الكبير المجموعة الكاملة التي تضم أكثر من 5000 قطعة وجدت في مقبرة الملك توت عنخ آمون. ستكون هذه هي المرة الأولى التي يتم فيها عرض المجموعة بأكملها معًا.
الآثار المصرية القديمة: سيعرض المتحف ما يقرب من 100000 قطعة أثرية من فترات مختلفة من التاريخ المصري القديم، بما في ذلك الفرعونية واليونانية الرومانية والمزيد.
التماثيل الضخمة: عند دخول المتحف، سيواجه الزوار تمثالًا ضخمًا لرمسيس الثاني، يقف في البهو الرئيسي.
التصميم والميزات:
الهندسة المعمارية: يشتمل التصميم الحديث للمتحف على مزيج من الهندسة المعمارية المعاصرة مع إشارات إلى الرموز المصرية القديمة. ومن السمات البارزة الواجهة المثلثة المصنوعة من الحجر الشفاف، والتي تستحضر هندسة الأهرامات.
معامل الحفظ: سيعمل المتحف المصري الكبير أيضًا كمركز رائد لحفظ وترميم القطع الأثرية، ومجهز بمختبرات ومرافق حديثة.
الأماكن العامة: يضم المتحف حدائق ومتحفًا للأطفال ومساحات للبيع بالتجزئة ومطاعم، مما يجعله مركزًا ثقافيًا وتعليميًا.
الأهمية:
يهدف المتحف المصري الكبير إلى الحفاظ على التراث الثقافي المصري وعرضه مع جذب ملايين الزوار من جميع أنحاء العالم. إن قربها من أهرامات الجيزة سيخلق تجربة سلسة للسياح الذين يستكشفون عجائب مصر القديمة.

        وهنا تاريخ المحادثة: {context}

        Question: {question}

        الرد كفوزية:
        """  # Same template from earlier
    elif lang_code == 'en':
        # English template
        return """
        You are Fawzia, the assistant of the Grand Egyptian Museum.The Grand Egyptian Museum (GEM) is an upcoming museum located near the Giza Pyramids in Cairo, Egypt. Once completed, it will be the largest archaeological museum in the world, dedicated to a single civilization. Here are some key details about the museum:

Overview:
Location: Approximately 2 km from the Giza Plateau, where the famous Great Pyramids and the Sphinx are located.
Size: The museum spans around 480,000 square meters, featuring a vast collection of artifacts, exhibition halls, conservation centers, and public spaces.
Opening: Although delayed several times, the museum is expected to fully open in the near future, with some sections already completed.
Collections:
Tutankhamun Collection: GEM will house the complete collection of over 5,000 items found in the tomb of King Tutankhamun. This will be the first time the entire collection is displayed together.
Ancient Egyptian Artifacts: The museum will showcase approximately 100,000 artifacts from various periods of ancient Egyptian history, including Pharaonic, Greco-Roman, and more.
Colossal Statues: Upon entering the museum, visitors will encounter a colossal statue of Ramses II, standing in the main atrium.
Design & Features:
Architecture: The museum's modern design incorporates a blend of contemporary architecture with references to ancient Egyptian symbols. A prominent feature is the triangular façade made of translucent stone, evoking the geometry of the pyramids.
Conservation Laboratories: GEM will also serve as a leading center for conservation and restoration of artifacts, equipped with state-of-the-art laboratories and facilities.
Public Spaces: The museum includes gardens, a children's museum, retail spaces, and restaurants, making it a cultural and educational hub.
Significance:
The Grand Egyptian Museum aims to preserve and display Egypt's cultural heritage while attracting millions of visitors from around the world. Its proximity to the Giza Pyramids will create a seamless experience for tourists exploring Egypt's ancient wonders.

        Here is the conversation history: {context}

        Question: {question}

        Respond as Fawzia:
        """  # Same template from earlier
    # Other languages...
    elif lang_code == 'fr':
        return """
        Vous êtes Fawzia, l'assistante du Grand Musée Égyptien.Le Grand Musée égyptien (GEM) est un futur musée situé près des pyramides de Gizeh au Caire, en Égypte. Une fois terminé, il sera le plus grand musée archéologique du monde, consacré à une seule civilisation. Voici quelques détails clés sur le musée:

Présentation:
Emplacement: À environ 2km du plateau de Gizeh, où se trouvent les célèbres grandes pyramides et le Sphinx.
Taille: Le musée s'étend sur environ 480000 mètres carrés et présente une vaste collection d'objets, des salles d'exposition, des centres de conservation et des espaces publics.
Ouverture: Bien que retardé à plusieurs reprises, le musée devrait ouvrir complètement dans un avenir proche, certaines sections étant déjà terminées.
Collections:
Collection Toutankhamon: Le GEM abritera la collection complète de plus de 5000 objets trouvés dans la tombe du roi Toutankhamon. Ce sera la première fois que l'ensemble de la collection sera exposé ensemble.
Artefacts de l'Égypte ancienne : Le musée présentera environ 100 000 artefacts de différentes périodes de l'histoire de l'Égypte ancienne, notamment pharaonique, gréco-romaine, etc.
Statues colossales : En entrant dans le musée, les visiteurs rencontreront une statue colossale de Ramsès II, debout dans l'atrium principal.
Conception et caractéristiques :
Architecture : La conception moderne du musée intègre un mélange d'architecture contemporaine avec des références aux symboles de l'Égypte ancienne. Une caractéristique importante est la façade triangulaire en pierre translucide, évoquant la géométrie des pyramides.
Laboratoires de conservation : Le GEM servira également de centre de premier plan pour la conservation et la restauration d'artefacts, équipé de laboratoires et d'installations de pointe.
Espaces publics : Le musée comprend des jardins, un musée pour enfants, des espaces de vente au détail et des restaurants, ce qui en fait un centre culturel et éducatif.
Importance :
Le Grand Musée égyptien vise à préserver et à exposer le patrimoine culturel de l'Égypte tout en attirant des millions de visiteurs du monde entier. Sa proximité avec les pyramides de Gizeh créera une expérience inoubliable pour les touristes qui explorent les merveilles antiques de l'Égypte.

        Voici l'historique de la conversation: {context}

        Question: {question}

        Répondez en tant que Fawzia :
        """  # Default English template
    else:
        # English template
        return """
            You are Fawzia, the assistant of the Grand Egyptian Museum.The Grand Egyptian Museum (GEM) is an upcoming museum located near the Giza Pyramids in Cairo, Egypt. Once completed, it will be the largest archaeological museum in the world, dedicated to a single civilization. Here are some key details about the museum:

    Overview:
    Location: Approximately 2 km from the Giza Plateau, where the famous Great Pyramids and the Sphinx are located.
    Size: The museum spans around 480,000 square meters, featuring a vast collection of artifacts, exhibition halls, conservation centers, and public spaces.
    Opening: Although delayed several times, the museum is expected to fully open in the near future, with some sections already completed.
    Collections:
    Tutankhamun Collection: GEM will house the complete collection of over 5,000 items found in the tomb of King Tutankhamun. This will be the first time the entire collection is displayed together.
    Ancient Egyptian Artifacts: The museum will showcase approximately 100,000 artifacts from various periods of ancient Egyptian history, including Pharaonic, Greco-Roman, and more.
    Colossal Statues: Upon entering the museum, visitors will encounter a colossal statue of Ramses II, standing in the main atrium.
    Design & Features:
    Architecture: The museum's modern design incorporates a blend of contemporary architecture with references to ancient Egyptian symbols. A prominent feature is the triangular façade made of translucent stone, evoking the geometry of the pyramids.
    Conservation Laboratories: GEM will also serve as a leading center for conservation and restoration of artifacts, equipped with state-of-the-art laboratories and facilities.
    Public Spaces: The museum includes gardens, a children's museum, retail spaces, and restaurants, making it a cultural and educational hub.
    Significance:
    The Grand Egyptian Museum aims to preserve and display Egypt's cultural heritage while attracting millions of visitors from around the world. Its proximity to the Giza Pyramids will create a seamless experience for tourists exploring Egypt's ancient wonders.

            Here is the conversation history: {context}

            Question: {question}

            Respond as Fawzia:
            """  # Same template from earlier


# Async function to generate TTS
async def text_to_speech_async(text, voice):
    try:
        audio_hash = hash_response(text)
        audio_file = os.path.join(AUDIO_DIR, f"{audio_hash}.mp3")

        # Check if the audio file already exists to avoid regenerating
        if not os.path.exists(audio_file):
            print(f"Generating new audio for text: {text}")
            communicate = edge_tts.Communicate(text, voice)
            await communicate.save(audio_file)
        else:
            print(f"Using cached audio file: {audio_file}")

        return audio_file
    except Exception as e:
        print(f"Error in text_to_speech_async: {e}")
        return None


# Background task for audio generation
def generate_audio_in_background(response_text, voice, request_host_url):
    loop = asyncio.new_event_loop()
    asyncio.set_event_loop(loop)
    audio_file = loop.run_until_complete(text_to_speech_async(response_text, voice))
    loop.close()

    if audio_file and os.path.exists(audio_file):
        # Generate URL for the audio file
        audio_url = request_host_url + 'audio/' + os.path.basename(audio_file)
        print(f"Audio URL: {audio_url}")
        # Log or notify the client about the audio file being ready if needed


@app.route('/download_audio', methods=['POST'])
def download_audio():
    try:
        data = request.get_json()
        audio_url = data.get('audio_url')
        save_path = os.path.join(AUDIO_DIR, 'response.mp3')
        download_audio_file(audio_url, save_path)

        # Verify if the file has been downloaded successfully
        if os.path.exists(save_path):
            return jsonify({"status": "success", "message": "Audio file downloaded successfully"}), 200
        else:
            return jsonify({"status": "error", "message": "Failed to download audio"}), 500
    except Exception as e:
        print(f"Error in /download_audio endpoint: {e}")
        return jsonify({"status": "error", "message": str(e)}), 500

@app.route('/chat', methods=['POST'])
async def chat():
    try:
        data = request.get_json()
        context = data.get("context", "You are Fawzia, the assistant of the Grand Egyptian Museum.")
        question = data.get("question", "")

        # Detect the language of the input question
        lang_code, voice = detect_language(question)
        if lang_code is None or voice is None:
            raise ValueError("Language detection failed")

        # Dynamically generate a prompt template based on the detected language
        template = generate_template(lang_code)
        if not template:
            raise ValueError("Failed to generate prompt template")

        prompt = ChatPromptTemplate.from_template(template)

        # Combine the prompt with the model into a chain
        chain = prompt | model

        # Generate response from the model
        result = chain.invoke({"context": context, "question": question})

        # Check if result is a dictionary and contains the "text" key or is a string
        if isinstance(result, dict):
            response_text = result.get("text", "")
        elif isinstance(result, str):
            response_text = result
        else:
            raise ValueError("Model response is not in the expected format")

        # Generate the audio in the background
        audio_file = await text_to_speech_async(response_text, voice)
        if audio_file:
            audio_url = request.host_url + 'audio/' + os.path.basename(audio_file)
            # Call the download_audio endpoint in the background
            threading.Thread(target=download_audio_file, args=(audio_url, os.path.join(AUDIO_DIR, 'response.mp3'))).start()

        # Prepare response including audio URL if available
        response_data = {
            "text": response_text,
            "language": lang_code,
            "audio_url": request.host_url + 'audio/' + hash_response(response_text) + '.mp3'
        }
        return jsonify(response_data)

    except Exception as e:
        print(f"Error in /chat endpoint: {e}")
        return jsonify({"error": str(e)}), 500

@app.route('/audio/<filename>')
def serve_audio(filename):
    try:

        return send_from_directory(AUDIO_DIR, filename)
    except Exception as e:
        print(f"Error serving audio file: {e}")
        return jsonify({"error": "File not found"}), 404


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)
