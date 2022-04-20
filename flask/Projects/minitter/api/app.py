from flask import Flask, jsonify, request

# Flask 클래스를 객체화 (instantiate)시켜서 app 에 저장
# 이 변수 app이 API어플리케이션이 된다
app = Flask(__name__)

# 회원가입 관련 변수
app.users = {} #사용자를 저장하는 디렉토리 (key : 사용자 아이디)
app.id_count = 1

# tweet 관련 변수
app.tweets = [] #트윗들을 저장하는 디렉터리 (key : 사용자 아이디)


# ping 테스트
@app.route("/ping", methods=['GET'])
def ping() :
    return "pong"


# 회원가입 기능
@app.route("/sign-up", methods=['POST'])
def sign_up() :
	new_user				= request.json #request를 json형태로 받아온다
	new_user['id']	 		= app.id_count
	app.users[app.id_count]	= new_user
	app.id_count			= app.id_count + 1

	return jsonify(new_user)


# tweet 기능
@app.route('/tweet', methods=['POST'])
def tweet() :
	payload = request.json #요청으로 전송된 데이터 읽어들임
	user_id = int(payload['id']) # id 정보 추출
	tweet = payload['tweet']

	# 사용자가 존재하지 않는 경우
	if user_id not in app.users:
		return '사용자가 존재하지 않습니다', 400

	# tweet 내용이 300을 넘는 경우
	if len(tweet) > 300 :
		return '300자를 초과했습니다', 300

	# 트윗 저장
	user_id = int(payload['id'])
	app.tweets.append({
		'user_id' : user_id,
		'tweet' : tweet
	})

	return '', 200
