
<template>
    <div>
        <div class="text-[34px] font-bold mb-[10px]">Curriculum Vitae</div>
        <div class="text-[18px] text-neutral-600 mb-4">Upload hier je CV in pdf format, probeer de volgende info er in
            te
            verwerken</div>
        <ol class="list-decimal list-inside text-[18px] text-neutral-600 mb-[16px]">
            <li>NAW</li>
            <li>Profiel foto</li>
            <li>Werk plekken</li>
            <li>Skills</li>
        </ol>
   
        <div>
            <div class="">
                <label for="resume-upload-input" class="cursor-pointer inline-block">
                    <div
                        class="mw-[186px] h-[44px] border-neutral-100 border-[2px] rounded-md flex justify-center items-center mb-5">

                        <font-awesome-icon icon="fa-file" class="text-neutral-700 text-[20px] ml-[10px] mr-[10px]" />
                        <div class="text-[16px] text-neutral-700 mt-[2px]">{{ (this.resumeFile ? this.resumeFile.name :
                                'Upload hier')
                        }}</div>
                        <div class="bg-primary-100 w-[20px] h-[20px] rounded-full ml-[10px] relative mt-[2px] mr-[15px]">
                            <font-awesome-icon icon="fa-arrow-down"
                                class="text-primary-500 text-[14px] absolute top-[3px] left-[5px]" />

                        </div>
                    </div>
                </label>
                <input @change="e => updateResume(e)" type="file" name="resume-upload-input" class="hidden"
                    id="resume-upload-input" accept=".pdf">
                <div class="flex">
                    <div class="bg-primary-500 text-neutral-0 active:bg-white:text-primary-500 flex justify-center items-center rounded-md w-[120px] h-[38px] mb-[10px] hover:bg-primary-600 capitalize font-bold text-[14px] text-center mr-5"
                        @click="handleProfileUpdate">
                        <div>CV bijwerken</div>
                    </div>

                    <div v-if="this.user?.resumeURL" class="bg-primary-500 text-neutral-0 active:bg-white:text-primary-500 flex justify-center items-center rounded-md w-[120px] h-[38px] mb-[10px] hover:bg-primary-600 capitalize font-bold text-[14px] text-center"
                        @click="toggleModal">
                        <div>View resume</div>
                    </div>
                </div>

       
                <div v-if="modalActive" class="bg-white absolute top-0 bottom-0 left-0 right-0 z-10 ">
                   
                    <object v-if="pdfSrc" :data="pdfSrc" class="w-full h-full pt-[60px]" type="application/pdf">
                
                    </object>

                    <div class="bg-primary-500 text-neutral-0 active:bg-white:text-primary-500 flex justify-center items-center rounded-md w-[120px] h-[38px] mb-[10px] hover:bg-primary-600 capitalize font-bold text-[14px] text-center fixed bottom-0 left-1/2 -translate-x-1/2"
                        @click="toggleModal">
                        <div>close window</div>
                    </div>
                </div>

            </div>
        </div>
    </div>
</template>
  
<style scoped>
.pdf-container {
    left: 40%;
    transform: translateX(-40%);
    width: 90vw;
}
</style>

<script>
import VuePdfEmbed from 'vue-pdf-embed'

export default {
    name: "Resume",
    components: {
        VuePdfEmbed
    },
    computed: {
        pdfSrc() {
            return this.fetchService.getAsset(this.user.resumeURL);
        },
    },
    inject: ['fetchService', 'userFetchService'],
    data() {
        return {
            user: JSON.parse(localStorage.getItem("user")),
            resumeFile: null,
            modalActive: false
        }
    },
    methods: {
        async toggleModal() {
            this.modalActive = !this.modalActive;
        },
        async handleProfileUpdate() {

            const formData = new FormData();

            if (this.resumeFile != null) formData.append('resumeFile', this.resumeFile);

            this.userFetchService.fetchJsonFile(`/${this.user.id}/resume`, "PUT", formData).then((response) => {
                localStorage.setItem("user", JSON.stringify(response));
            })

            this.resumeFile = null;
        },
        async updateResume(event) {
            console.log(this.user)
            if (event.target.files === 0) {
                console.log(event.target.files[0].name)
                return;
            }

            const fileExtension = event.target.files[0].name.split('.').pop();
            if (!['pdf'].includes(fileExtension)) {
                // this.errors.logo = "Alleen afbeeldingen met de extensies .svg, .png, .webp, .jpg en .jpeg zijn toegestaan.";
                return;
            }

            console.log(event.target.files[0])
            this.resumeFile = event.target.files[0];
        },
        async getBase64(file) {
            // convert javascript file to base64 string
            return new Promise((resolve, reject) => {
                const reader = new FileReader();
                reader.readAsDataURL(file);
                reader.onload = () => resolve(reader.result);
                reader.onerror = error => reject(error);
            });
        }
    }
}
</script>