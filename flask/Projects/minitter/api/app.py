from flask import Flask, jsonify, request
from flask.json import JSONEncoder

# Flask 클래스를 객체화 (instantiate)시켜서 app 에 저장
# 이 변수 app이 API어플리케이션이 된다
app = Flask(__name__)

# util
app.json_encoder = CustomJSONEncoder # set을 list로 변경


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


# follow 기능
@app.route('/follow', methods=['POST'])
def follow() :
	# request에서 정보 읽어들이고, 변수로 추출하기
	payload					= request.json
	user_id					= int(payload['id'])
	user_id_to_follow		= int(payload['follow'])

	# 사용자가 존재하지 않을경우 error
	if user_id not in app.users or user_id_to_follow not in app.users :
		return '사용자가 존재하지 않습니다.', 400

	# 읽어들인 사용자 정보를 담고있는 딕셔너리가 이미 'follow' 필드 가지고 있으면(이미 다른 사용자 팔로한 적 있음)
	# 사용자의 'follow' 키와 연결되어있는 set에 팔로우하고자 하는 사용자 아이디 축
	# 첫 팔로우라면, user딕셔너리에 'follow'라는 키를 empty set과 연결하여 추가한다.
	user = app.users[user_id]
	user.setdefault('follow', set()).add(user_id_to_follow)

	return jsonify(user)


# unfollow 기능
@app.route('/unfollow', methods=['POST'])
def unfollow() :
	payload				= request.json
	user_id				= int(payload['id'])
	user_id_to_follow	= int(payload['unfollow'])

	# 사용자가 존재하지 않을경우 error
	if user_id not in app.users or user_id_to_follow not in app.users :
		return '사용자가 존재하지 않습니다.', 400

	# 언팔로우하고자 하는 사용자 아이디를 set에서 삭제한다
	user = app.users[user_id]
	user.setdefault('follow', set()).discard(user_id_to_follow)

	return jsonify(user)


# timeline 기증
@app.route('/timeline/<int:user_id>', methods=['GET'])
def timeline(user_id) :
	if user_id not in app.users:
		return '사용자가 존재하지 않습니다', 400

	# followe list를 받아온다
	follow_list = app.users[user_id].get('follow', set())
	follow_list.add(user_id) # 자기 자신도 추가한다

	# 전체 트윗 중 follow list에 있는 사용자들의 트윗만 읽어들인다
	timeline = [tweet for tweet in app.tweets if tweet['user_id']
	in follow_list]

	# 사용자 아이디와 함께 타임라인을 JSON 형태로 리턴한다
	return jsonify({
		'user_id' : user_id,
		'timeline' : timeline
	})


############################
######### utils
############################

# JSON으로 변경하고자 하는 객체(obj)가 set인 경우 list로 변경해서 리턴한다
class CustomJSONEncoder(JSONEncoder) :
	def default(self, obj) : # overwrite
		if isinstance(obj, set) :
			return list(obj)

		return JSONEncoder.default(self, obj)

