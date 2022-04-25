import {readAsArrayBuffer} from "promise-file-reader";


export default async function readFileAsBlob(file) {
    return file ? await readAsArrayBuffer(file) : null
}